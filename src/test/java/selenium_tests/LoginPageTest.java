package selenium_tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPageTest extends AbstractPage {

    private WebElement username;

    private WebElement password;

    @FindBy(css = ".btn.btn-lg.btn-primary.btn-block")
    private WebElement submit;

    public LoginPageTest(WebDriver driver) {
        super(driver);
    }


    public static LoginPageTest openLogin(WebDriver driver) {
        get(driver, "/login");
        System.out.println(driver.getCurrentUrl());
        return PageFactory.initElements(driver, LoginPageTest.class);

    }

    public static CoursesPageTest doLogin(WebDriver driver, LoginPageTest loginPage, String username, String password) {
        loginPage.username.sendKeys(username);
        loginPage.password.sendKeys(password);
        loginPage.submit.click();
        System.out.println(driver.getCurrentUrl());
        return PageFactory.initElements(driver, CoursesPageTest.class);
    }

    public static LoginPageTest logout(WebDriver driver) {
        get(driver, "/logout");
        return PageFactory.initElements(driver, LoginPageTest.class);
    }
}
