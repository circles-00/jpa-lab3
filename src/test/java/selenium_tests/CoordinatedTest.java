package selenium_tests;


import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class CoordinatedTest {
    private HtmlUnitDriver driver;
    private static String admin = "admin";

    @Autowired
    CourseService courseService;

    @Autowired
    TeacherService teacherService;

    private static boolean dataInitialized = false;


    @BeforeEach
    public void setup() throws Exception {
        this.driver = new HtmlUnitDriver(true);
        initData();
    }

    private void initData() throws Exception {
        if(!dataInitialized)
        {
            dataInitialized = true;
            Teacher t = new Teacher("Teacher1","Teacher1");
            teacherService.saveTeacher(t);
            courseService.saveCourse("Course1","CourseDesc", t.getId());
        }
    }

    @AfterEach
    public void destroy() {
        if (this.driver != null) {
            this.driver.close();
        }
    }

    @Test
    public void testScenario() {
        CoursesPageTest coursesPage = CoursesPageTest.to(new HtmlUnitDriver(true));
        coursesPage.assertAbsentElements();
        LoginPageTest loginPage = LoginPageTest.openLogin(new HtmlUnitDriver(true));

        coursesPage = LoginPageTest.doLogin(new HtmlUnitDriver(true), loginPage, admin, "password");
        coursesPage.assertPresentElements();
    }
}
