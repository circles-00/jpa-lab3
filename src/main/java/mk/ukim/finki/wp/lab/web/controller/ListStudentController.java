package mk.ukim.finki.wp.lab.web.controller;

import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller()
@RequestMapping("/AddStudent")
@Slf4j

public class ListStudentController {
    private final StudentService studentService;
    private final CourseService courseService;
    private final GradeRepository gradeRepository;

    public ListStudentController(StudentService studentService, CourseService courseService, GradeRepository gradeRepository) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.gradeRepository = gradeRepository;
    }

    @GetMapping
    public String doGet(Model model, HttpSession session) {
        Long courseId = Long.parseLong(String.valueOf(session.getAttribute("courseId")));
        model.addAttribute("courseId", courseId);
        List<Student> studentList = this.studentService.listAll();
        List<Student> filterList= this.courseService.listStudentsByCourse(courseId);
        List<Student> finalFiltered= this.studentService.getFilteredStudents(filterList);
        model.addAttribute("studentList", finalFiltered);

        return "listStudents";
    }


    @PostMapping
    public String doPost(@RequestParam String studentUsername,
                         @RequestParam(name = "gradeDatetime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime gradeDatetime,
                         @RequestParam int grade,
                         HttpSession session) {
        Long courseId = Long.parseLong(String.valueOf(session.getAttribute("courseId")));

        log.info("LOGGING DATETIME " + gradeDatetime);
        this.courseService.addStudentInCourse(studentUsername, courseId);
        this.gradeRepository.save(new Grade(grade, gradeDatetime, this.studentService.getByUsername(studentUsername), this.courseService.findById(courseId)));

        return "redirect:/StudentEnrollmentSummary";
    }
}