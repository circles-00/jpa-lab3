package mk.ukim.finki.wp.lab.web.controller;

import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import mk.ukim.finki.wp.lab.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller()
@RequestMapping("/courses")
@Slf4j
public class CourseController {

    private final CourseService courseService;
    private final TeacherService teacherService;

    public CourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public String getCoursesPage(@RequestParam(required = false) String error, Model model, @RequestParam(required = false) String sortOrder) {
        List<Course> coursesList = CommonUtils.sortCourses(this.courseService.listAll(), sortOrder != null ? sortOrder : "ascending");
        String sortingOrder = CommonUtils.getNextSortOrder();

        model.addAttribute("coursesList", coursesList);
        model.addAttribute("error", error);
        model.addAttribute("sortOrder", sortingOrder);

        return "listCourses";
    }

    @PostMapping("/sort")
    public String sortCourses(RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("sortOrder", CommonUtils.getNextSortOrder());

        return "redirect:/courses";
    }

    @PostMapping
    public String postCoursesPage(@RequestParam(required = false) Long courseId, @RequestParam(required = false) String action, Model model, HttpSession session) {
        if(action != null && !action.equals("submit")) {
            String realAction = action.split("-")[0];
            Long courseIdFromAction = Long.parseLong(action.split("-")[1]);
            log.info("ACTION: " + realAction + " " + courseIdFromAction);

            switch (realAction) {
                case "edit":
                    return "redirect:/courses/edit-form/" + courseIdFromAction;
                case "delete":
                    return "";
            }
        }

        session.setAttribute("courseId", courseId);

        return "redirect:/AddStudent";
    }

    @PutMapping("/add")
    public String saveCourse(@RequestParam String courseName, @RequestParam String courseDescription, @RequestParam Long teacherId, RedirectAttributes redirectAttributes) {
        try {
            this.courseService.saveCourse(courseName, courseDescription, teacherId);

            return "redirect:/courses";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/courses/add-form";
        }
    }

    @PatchMapping("/edit")
    public String editCourse(@RequestParam Long courseId, @RequestParam String courseName, @RequestParam String courseDescription, @RequestParam Long teacherId) {
        try {
            this.courseService.editCourse(courseId, courseName, courseDescription, teacherId);

            return "redirect:/courses";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/courses/add-form";
        }
    }


    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteCourse(@PathVariable Long id) {
        try {
            this.courseService.deleteCourse(id);

            return "redirect:/courses";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/courses";
        }
    }

    @GetMapping("/add-form")
    public String getAddCoursePage(Model model, @RequestParam(required = false) String error) {
        List<Teacher> teachers = this.teacherService.findAll();
        model.addAttribute("teachers", teachers);
        model.addAttribute("method", "PUT");
        model.addAttribute("type", "add");
        model.addAttribute("error", error);
        return "add-course";
    }

    @GetMapping("/edit-form/{id}")
    public String getEditCoursePage(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Course course = this.courseService.findById(id);
            List<Teacher> teachers = this.teacherService.findAll();
            model.addAttribute("teachers", teachers);
            model.addAttribute("method", "PATCH");
            model.addAttribute("type", "edit");
            model.addAttribute("courseId", id);
            model.addAttribute("courseName", course.getName());
            model.addAttribute("courseDescription", course.getDescription());
            model.addAttribute("teacherId", course.getTeacher() != null ? course.getTeacher().getId() : "");

            return "add-course";
        } catch (NullPointerException e) {
            redirectAttributes.addAttribute("error", String.format("The course with id: %d doesn't exist", id));

            return "redirect:/courses";
        }
    }
}
