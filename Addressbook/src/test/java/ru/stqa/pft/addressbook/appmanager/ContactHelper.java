package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {


    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        wd.findElement(By.xpath("(//input[@name='submit'])[2]")).click();
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("email"), contactData.getEmail());
        type(By.name("address"), contactData.getAddress());

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }

    }

    public void chooseContact() {
        wd.findElement(By.xpath("(//input[@name='selected[]'])[1]")).click();
    }

    public void submitDeleteContact() {
        wd.findElement(By.xpath("//input[@value='Delete']")).click();
    }


    public void initContactModification(int index) {
        wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
    }

    public void submitContcactModification() {
        click(By.xpath("(//input[@name='update'])[2]"));
    }


    public void createContact(ContactData contact, boolean creation) {
        fillContactForm(contact, creation);
        submitContactCreation();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.xpath("(//input[@name='selected[]'])[1]"));
    }

    public int getContactCount() {
        return wd.findElements(By.name("selected[]")).size();
    }
}
