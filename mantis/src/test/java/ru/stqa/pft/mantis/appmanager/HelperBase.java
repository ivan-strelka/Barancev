package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;

public class HelperBase {

    protected ApplicationManager app;
    protected WebDriver wd;

    public HelperBase(ApplicationManager app) {
        this.app = app;
        this.wd = app.getDriver();
    }

}


