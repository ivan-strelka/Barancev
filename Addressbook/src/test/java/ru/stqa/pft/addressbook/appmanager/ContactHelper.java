package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper {

    private ChromeDriver wd;

    public ContactHelper(ChromeDriver wd) {
        this.wd = wd;

    }

    public void submitContactCreation() {
        wd.findElement(By.xpath("(//input[@name='submit'])[2]")).click();
    }

    public void fillContactForm(ContactData contactData) {
        wd.findElement(By.name("firstname")).sendKeys(contactData.getFirstName());
        wd.findElement(By.name("lastname")).sendKeys(contactData.getLastName());
        wd.findElement(By.name("email")).sendKeys(contactData.getEmail());
        wd.findElement(By.name("address")).sendKeys(contactData.getAddress());

    }

    public void chooseContact() {
        wd.findElement(By.xpath("(//input[@name='selected[]'])[1]")).click();
    }

    public void submitDeleteContact() {
        wd.findElement(By.xpath("//input[@value='Delete']")).click();
    }


}
