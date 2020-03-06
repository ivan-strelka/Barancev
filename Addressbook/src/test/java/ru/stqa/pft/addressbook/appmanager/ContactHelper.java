package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

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

    public void chooseContact(int index) {
        wd.findElements(By.xpath("(//input[@name='selected[]'])")).get(index).click();
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

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));
        for (WebElement element : elements) {
            //String firstname = element.findElement(By.xpath("//tr[*]//td[3]")).getText();
            String email = element.findElement(By.tagName("input")).getAttribute("accept");
            String id = element.findElement(By.tagName("input")).getAttribute("value");
            ContactData contact = new ContactData(id, null, null, email, null, null);
            contacts.add(contact);
        }
        return contacts;


    }
}
