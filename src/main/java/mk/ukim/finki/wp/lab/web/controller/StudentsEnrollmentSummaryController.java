package mk.ukim.finki.wp.lab.web.controller;

import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/StudentEnrollmentSummary")
@Slf4j
public class StudentsEnrollmentSummaryController {
    private final SpringTemplateEngine springTemplateEngine;
    private final CourseService courseService;
    private final GradeRepository gradeRepository;

    public StudentsEnrollmentSummaryController(SpringTemplateEngine springTemplateEngine, CourseService courseService, GradeRepository gradeRepository) {
        this.springTemplateEngine = springTemplateEngine;
        this.courseService = courseService;
        this.gradeRepository = gradeRepository;
    }

    @GetMapping
    public String doGet(
            @RequestParam(name = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(name = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate,
            Model model,
            HttpSession session) {
        Long courseId = Long.parseLong(String.valueOf(session.getAttribute("courseId")));
        Course course = this.courseService.findById(courseId);

        log.info("COURSE NO STUDENTS: " + course.getStudents().size());
        List<Grade> grades = new ArrayList<>();
        List<Student> studentList = new ArrayList<>();

        model.addAttribute("courseName", course.getName());
        course.getStudents().forEach(student -> {
            Grade grade;
            if(fromDate == null) {
                grade = this.gradeRepository.findByStudentAndCourse(student.getUsername(), courseId);
            } else {
                grade = this.gradeRepository.findGradeByTimestampBetween(student.getUsername(), courseId, fromDate, toDate);
            }

            if(grade != null){
                grades.add(grade);
                studentList.add(student);
            }
        });
        model.addAttribute("studentList", studentList);
        model.addAttribute("grades", grades);

        return "studentsInCourse";
    }
}