package mk.ukim.finki.wp.lab.web;

import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StudentEnrollmentSummary", urlPatterns = "/StudentEnrollmentSummaryBak")
@Slf4j
public class StudentEnrollmentSummary extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private final CourseService courseService;
    private final GradeRepository gradeRepository;

    public StudentEnrollmentSummary(SpringTemplateEngine springTemplateEngine, CourseService courseService, GradeRepository gradeRepository) {
        this.springTemplateEngine = springTemplateEngine;
        this.courseService = courseService;
        this.gradeRepository = gradeRepository;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext webContext = new WebContext(req,resp, req.getServletContext());
        Long courseId = Long.parseLong(String.valueOf(req.getSession().getAttribute("courseId")));
        String studentUsername = (String) req.getSession().getAttribute("studentUsername");
        String gradeFromSession = (String) req.getSession().getAttribute("grade");
        String gradeDateTime = (String) req.getSession().getAttribute("gradeDateTime");
        log.info("GRADE DATE TIME ALO " + gradeDateTime);

        Grade grade = gradeRepository.findByStudentAndCourse(studentUsername, courseId);
        this.courseService.addStudentInCourse(studentUsername, courseId);
        webContext.setVariable("studentsInCourse", this.courseService.listStudentsByCourse(courseId));
        webContext.setVariable("courseName", this.courseService.findById(courseId).getName());
        webContext.setVariable("grade", grade.getGrade());

        this.springTemplateEngine.process("studentsInCourse.html", webContext, resp.getWriter());
    }
}
