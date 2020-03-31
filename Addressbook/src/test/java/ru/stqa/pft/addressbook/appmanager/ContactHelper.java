package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

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
            if (contactData.getGroups().size() > 0) {
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group")))
                        .selectByVisibleText(contactData.getGroups().iterator().next().getName());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }

    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void submitDeleteContact() {
        wd.findElement(By.xpath("//input[@value='Delete']")).click();
    }

    public void editContactById(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();

    }


    public void submitContcactModification() {
        click(By.xpath("(//input[@name='update'])[2]"));
    }


    private Contacts contactCache = null;

    public boolean isThereAContact() {
        return isElementPresent(By.xpath("(//input[@name='selected[]'])[1]"));
    }

    public int Count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public void create(ContactData contact, boolean creation) {
        fillContactForm(contact, creation);
        submitContactCreation();
        contactCache = null;
    }

    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }

        contactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            List<WebElement> cells = element.findElements(By.tagName("td"));
            String firstName = cells.get(2).getText();
            String lastName = cells.get(1).getText();
            String allPhones = cells.get(5).getText();
            String address = cells.get(3).getText();
            String allEmail = cells.get(4).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            contactCache.add(new ContactData()
                    .withId(id)
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withAllPhone(allPhones)
                    .withAddress(address)
                    .setAllEmail(allEmail));
        }

        return new Contacts(contactCache);
    }


    public void modify(ContactData contactData) {
        editContactById(contactData.getId());
        fillContactForm(contactData, false);
        submitContcactModification();
        contactCache = null;
    }

    public void delete(ContactData deletedContact) {
        selectContactById(deletedContact.getId());
        submitDeleteContact();
        contactCache = null;
    }


    public ContactData infoFromEditForm(ContactData contactData) {
        initContactModificationById(contactData.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        String address = wd.findElement(By.cssSelector("textarea[name = 'address']")).getText();
        wd.navigate().back();

        return new ContactData().withId(contactData.getId()).withFirstName(firstname).withLastName(lastname)
                .withHomePhone(home)
                .withMobilePhone(mobile)
                .withWorkPhone(work)
                .withEmail(email)
                .withEmail2(email2)
                .withEmail3(email3)
                .withAddress(address);
    }


    private void initContactModificationById(int id) {
        WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value ='%s']", id)));
        WebElement row = checkbox.findElement(By.xpath("./../.."));
        List<WebElement> cells = row.findElements(By.tagName("td"));
        cells.get(7).findElement(By.tagName("a")).click();
    }


    public void addContactToGroup(String groupName, int contactId) {
        wd.findElement(By.xpath("//input[@type='checkbox'and @id='" + contactId + "']")).click();
        wd.findElement(By.name("to_group")).click();
        new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(groupName);
        wd.findElement(By.name("add")).click();
    }


    public void deleteContactFromGroup(String groupName, int contactId) {
        wd.findElement(By.name("group")).click();
        new Select(wd.findElement(By.name("group"))).selectByVisibleText(groupName);
        wd.findElement(By.xpath("//input[@type='checkbox'and @id='" + contactId + "']")).click();
        wd.findElement(By.name("remove")).click();
    }

    public ContactData addContactToGroup(ContactData contact, GroupData group) {
        selectContactById(contact.getId());
        chooseContactForAddingToGroup(group, group.getId());
        return new ContactData().inGroup(group).withId(contact.getId()).withFirstName(contact.getFirstName())
                .withLastName(contact.getLastName()).withAddress(contact.getAddress());
    }


    public ContactData chooseContactForAddingToGroup(GroupData group, int id) {
        WebElement chooseGroup = wd.findElement(By.xpath("//select[@name='to_group']//option[@value='" + id + "']"));
        chooseGroup.click();
        click(By.name("add"));
        return new ContactData().inGroup(group);
    }

    public void removeContactFromGroup(ContactData contact, GroupData group) {
        selectContactById(contact.getId());
        chooseGroupFromList(group.getId());
        initContactActionWithGroupById(contact.getId());
        submitContactDeletionFromGroup();
        chooseAllGroupsFromTheList();
    }

    private void chooseGroupFromList(int id) {
        wd.findElement(By.cssSelector("option[value='" + id + "']")).click();
    }

    private void initContactActionWithGroupById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    private void submitContactDeletionFromGroup() {
        click(By.name("remove"));
    }

    private void chooseAllGroupsFromTheList() {
        click(By.linkText("home"));
        wd.findElement(By.cssSelector("option[value='']")).click();
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }


}
