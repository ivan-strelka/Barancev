package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTest extends TestBase {


    @BeforeMethod
    public void ensurePreconditions() {
        Groups groups = app.db().getGroups();
        if (app.db().contacts().size() == 0) {
            app.goTo().addContactPage();
            app.goToCont().create(new ContactData().withFirstName((properties.getProperty("web.firstName")))
                    .withLastName(properties.getProperty("web.lastName"))
                    .withEmail(properties.getProperty("web.email"))
                    .withAddress(properties.getProperty("web.address"))
                    .inGroup(groups.iterator().next()), true);
        }
    }

    @Test
    public void testModificationContact() throws IOException {
        Groups groups = app.db().getGroups();
        properties = new Properties();
        properties.load(new FileReader(new File(String.format("src/test/resources/local.properties"))));
        Contacts before = app.db().contacts();
        ContactData modifyContact = before.iterator().next();
        ContactData contactData = new ContactData()
                .withId(modifyContact.getId())
                .withFirstName(properties.getProperty("web.firstName"))
                .withLastName(properties.getProperty("web.lastName"))
                .withEmail(properties.getProperty("web.email"))
                .withAddress(properties.getProperty("web.address"))
                .inGroup(groups.iterator().next());
        app.goTo().goToHomePage();
        app.goToCont().modify(contactData);
        app.goTo().goToHomePage();
        assertThat(app.goToCont().Count(), equalTo(before.size()));
        Contacts after = app.db().contacts();

        before.remove(modifyContact);
        before.add(contactData);
        Assert.assertEquals(before, after);
        assertThat(after, equalTo(before.withOut(modifyContact).withAdded(contactData)));


    }


}
