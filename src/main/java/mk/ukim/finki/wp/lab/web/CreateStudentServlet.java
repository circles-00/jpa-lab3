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

@WebServlet(name = "CreateStudentServlet", urlPatterns = "/CreateStudent")
@Slf4j
public class CreateStudentServlet extends HttpServlet {

    private SpringTemplateEngine springTemplateEngine;
    private StudentRepository studentRepository;

    public CreateStudentServlet(SpringTemplateEngine springTemplateEngine, StudentRepository studentRepository) {
        this.springTemplateEngine = springTemplateEngine;
        this.studentRepository = studentRepository;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext webContext = new WebContext(req, resp, req.getServletContext());

        this.springTemplateEngine.process("/createStudent.html", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");

        this.studentRepository.addStudent(new Student(username, password, name, surname));
        resp.sendRedirect("/AddStudent");
    }
}
