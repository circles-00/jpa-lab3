package mk.ukim.finki.wp.lab.web;

import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListStudentServlet", urlPatterns = "/AddStudent")
@Slf4j
public class ListStudentServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final StudentRepository studentRepository;

    public ListStudentServlet(SpringTemplateEngine springTemplateEngine, StudentRepository studentRepository) {
        this.springTemplateEngine = springTemplateEngine;
        this.studentRepository = studentRepository;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext webContext = new WebContext(req, resp, req.getServletContext());
        webContext.setVariable("courseId", req.getSession().getAttribute("courseId"));
        List<Student> studentList = this.studentRepository.findAllStudents();
        webContext.setVariable("studentList", studentList);

        this.springTemplateEngine.process("listStudents.html", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("studentUsername", req.getParameter("studentUsername"));

        resp.sendRedirect("/StudentEnrollmentSummary");
    }
}
