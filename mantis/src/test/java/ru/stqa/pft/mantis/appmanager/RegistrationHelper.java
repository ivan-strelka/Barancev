package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;

public class RegistrationHelper {
    private final ApplicationManager app;
    private WebDriver wd;


    public RegistrationHelper(ApplicationManager app) {
        this.app = app;
        wd = app.getDriver();
    }

    public void start(String user1, String s) {
        wd.get(app.getProperty("web.BaseUrl") + "/signup_page.php");
        System.out.println();
    }
}
