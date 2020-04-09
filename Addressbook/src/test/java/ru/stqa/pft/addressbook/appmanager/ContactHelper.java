package ru.stqa.pft.addressbook.appmanager;

import com.google.common.collect.Sets;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase {

    protected static final ApplicationManager app =
            new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

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


    public void addContactToGroup(ContactData contact) {
        selectContactById(contact.getId());
        wd.findElement(By.name("to_group")).click();
        selectGroup(contact, true);
        addToGroup();
        app.goTo().goToHomePage();
    }


    public void addToGroup() {
        wd.findElement(By.name("add")).click();
    }

    public void selectGroup(ContactData contactData, boolean selection) {

        int contactGroupSize = contactData.getGroups().size();
        int totalDBGroupSize = app.db().getGroups().size();
        int counter = 0;
        if (selection) {
            if (contactGroupSize == 0 || contactGroupSize == totalDBGroupSize) {

                List<ContactData> contactList = app.goToCont().getContactsList();
                for (ContactData contact : contactList) {
                    if (contact.getGroups().size() < totalDBGroupSize) {
                        app.goTo().goToHomePage();
                        selectContactById(contact.getId());
                        new Select(wd.findElement(By.name("to_group")))
                                .selectByValue(String.valueOf(getGroupListWithoutContact(contact.getGroups())
                                        .iterator().next().getId()));
                        counter++;
                        break;
                    }
                }
                if (counter == 0) {
                    app.goTo().addContactPage();
                    app.goToCont().create(new ContactData().withFirstName("ssdf12")
                            .withLastName("sdfsdf123"), true);
                    addContactToGroup(contactData);
                }
            } else {
                Groups totalContactGroups = contactData.getGroups();
                new Select(wd.findElement(By.name("to_group")))
                        .selectByValue(String.valueOf(getGroupListWithoutContact(totalContactGroups)
                                .iterator().next().getId()));
            }
        }
    }

    public void selectGroup(ContactData user, GroupData group) {
        selectContactById(user.getId());
        String groupId = String.valueOf(group.getId());
        new Select(wd.findElement(By.name("to_group"))).selectByValue(groupId);
        addUserToGroup();
    }

    public void addUserToGroup() {
        wd.findElement(By.name("add")).click();
    }


    public void allGroupsOnPage() {
        new Select(wd.findElement(By.name("group"))).selectByVisibleText("[all]");
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }


    public List<ContactData> getContactsList() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<ContactData> result = session.createQuery("from ContactData where deprecated = '0000-00-00'").list();
        session.close();
        return result;
    }

    public Set<GroupData> getGroupListWithoutContact(Groups totalContactGroups) {
        Groups totalGroups = app.db().getGroups();
        return Sets.difference(totalGroups, totalContactGroups);
    }


    public void addInGroup(ContactData cont, GroupData nameGroup) {
        wd.findElement(By.cssSelector("input[value = '" + cont.getId() + "']")).click();
        wd.findElement(By.name("to_group")).click();
        new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(nameGroup.getName());
        wd.findElement(By.name("add")).click();
        app.goTo().goToHomePage();
    }

    public void deletedGroup(ContactData cont, GroupData group, boolean have) {
        wd.findElement(By.linkText("home")).click();
        wd.findElement(By.name("group")).click();
        new Select(wd.findElement(By.name("group"))).selectByVisibleText(group.getName());
        if (have) {
            wd.findElement(By.cssSelector("input[value = '" + cont.getId() + "']")).click();
            wd.findElement(By.name("remove")).click();
            wd.findElement(By.linkText("home")).click();
        } else {
            new Select(wd.findElement(By.name("group"))).selectByVisibleText("[all]");
            addInGroup(cont, group);
            wd.findElement(By.name("group")).click();
            new Select(wd.findElement(By.name("group"))).selectByVisibleText(group.getName());
            wd.findElement(By.cssSelector("input[value = '" + cont.getId() + "']")).click();
            wd.findElement(By.name("remove")).click();
            wd.findElement(By.linkText("home")).click();
        }
    }

}
