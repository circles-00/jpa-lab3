package mk.ukim.finki.wp.lab.web;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "coursesList", urlPatterns = "/listCourses")
public class CoursesListServlet extends HttpServlet {
    private final CourseService courseService;
    private final SpringTemplateEngine springTemplateEngine;
    public CoursesListServlet(CourseService courseService, SpringTemplateEngine springTemplateEngine) {
        this.courseService = courseService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext webContext = new WebContext(req, resp, req.getServletContext());

        List<Course> coursesList = this.courseService.listAll();
        webContext.setVariable("coursesList", coursesList);


        this.springTemplateEngine.process("listCourses.html", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("courseId", req.getParameter("courseId"));

        resp.sendRedirect("/AddStudent");
    }
}
