package mk.ukim.finki.wp.lab.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
@Slf4j
public class CourseFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String path = new UrlPathHelper().getPathWithinApplication(req);
        String courseId = req.getSession().getAttribute("courseId") == null ? null : String.valueOf(req.getSession().getAttribute("courseId"));

        String homepagePath = "/courses";
        log.info("Requested: " + req.getMethod() + " " + path);
        if(courseId == null && !path.equals(homepagePath) && !path.equals("/courses/add-form") && !path.contains("/courses/edit-form") && !path.equals("/courses/add") && !req.getMethod().equals("DELETE") && !path.contains("/courses/sort") && !path.contains("/courses/edit")) {
            resp.sendRedirect(homepagePath);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
