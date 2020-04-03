package ru.stqa.pft.mantis.test;

import org.hibernate.service.spi.ServiceException;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Properties;

public class TestBase {

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

    public boolean isIssueOpen(int issueId) throws RemoteException, ServiceException, MalformedURLException, javax.xml.rpc.ServiceException {
        String status = app.soap().getIssueStatus(issueId);
        System.out.println(status);
        if (status.equals("resolved")) {
            return false;
        }
        return true;
    }

    public void skipIfNotFixed(int issueId) throws RemoteException, ServiceException, MalformedURLException, javax.xml.rpc.ServiceException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }


}
