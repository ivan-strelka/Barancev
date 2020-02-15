package ru.stqa.pft.addressbook;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class GroupCreationTest {
    private WebDriver wd;

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "utils/chromedriver");
        wd = new ChromeDriver();
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testGroupCreation() throws Exception {
        wd.get("http://localhost/addressbook/index.php");
        wd.findElement(By.xpath("//input[@name='user']")).clear();
        wd.findElement(By.xpath("//input[@name='user']")).sendKeys("admin");
        wd.findElement(By.xpath("//input[@name='pass']")).clear();
        wd.findElement(By.xpath("//input[@name='pass']")).sendKeys("secret");
        wd.findElement(By.xpath("//input[@value='Login']")).click();
        wd.findElement(By.linkText("groups")).click();
        wd.findElement(By.name("new")).click();
        wd.findElement(By.name("group_name")).click();
        wd.findElement(By.name("group_name")).clear();
        wd.findElement(By.name("group_name")).sendKeys("aaa");
        wd.findElement(By.name("group_header")).click();
        wd.findElement(By.name("group_header")).clear();
        wd.findElement(By.name("group_header")).sendKeys("bbb");
        wd.findElement(By.name("group_footer")).click();
        wd.findElement(By.name("group_footer")).clear();
        wd.findElement(By.name("group_footer")).sendKeys("ccc");
        wd.findElement(By.name("submit")).click();
        wd.findElement(By.linkText("group page")).click();
        wd.findElement(By.linkText("Logout")).click();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        wd.quit();

    }

    private boolean isElementPresent(By by) {
        try {
            wd.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }


}
