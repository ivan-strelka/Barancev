package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Properties;

public class TestBase {

    Logger logger = LoggerFactory.getLogger(TestBase.class);

    protected static final ApplicationManager app =
            new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
    public Properties properties;

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
        properties = new Properties();
        properties.load(new FileReader(new File(String.format("src/test/resources/local.properties"))));

    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method m, Object[] p) {
        logger.info("START TEST " + m.getName() + "with parameters" + Arrays.asList(p));
    }


    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method m) {
        logger.info("STOP TEST " + m.getName());
    }


}
