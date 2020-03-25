package ru.stqa.pft.addressbook.tests;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {
    protected SessionFactory sessionFactory;
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


    public void verifyGroupListInUI() {
        if (Boolean.getBoolean("verifyUI")) {
            Groups dbGroups = app.db().groups();
            Groups uiGroups = app.group().all();
            assertThat(uiGroups, equalTo(dbGroups.stream().map((g) -> new GroupData()
                    .withId(g.getId())
                    .withName(g.getName()))
                    .collect(Collectors.toSet())));
        }
    }

    @BeforeClass
    protected void setUpDb() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}
