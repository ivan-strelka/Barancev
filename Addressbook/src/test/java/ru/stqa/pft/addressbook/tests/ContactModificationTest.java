package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.goToCont().all().size() == 0) {
            app.goTo().ContactPage();
            app.goToCont().create(new ContactData().withFirstName((properties.getProperty("web.firstName")))
                    .withLastName(properties.getProperty("web.lastName"))
                    .withEmail(properties.getProperty("web.email"))
                    .withAddress(properties.getProperty("web.address"))
                    .withGroup(properties.getProperty("web.group")), true);
        }
    }

    @Test
    public void testModificationContact() throws IOException {
        properties = new Properties();
        properties.load(new FileReader(new File(String.format("src/test/resources/local.properties"))));
        Contacts before = app.goToCont().all();
        ContactData modifyContact = before.iterator().next();
        ContactData contactData = new ContactData()
                .withId(modifyContact.getId())
                .withFirstName(properties.getProperty("web.firstName"))
                .withLastName(properties.getProperty("web.lastName"))
                .withEmail(properties.getProperty("web.email"))
                .withAddress(properties.getProperty("web.address"))
                .withGroup(properties.getProperty("web.group"));
        app.goTo().goToHomePage();
        app.goToCont().modify(contactData);
        app.goTo().goToHomePage();
        assertThat(app.goToCont().Count(), equalTo(before.size()));
        Contacts after = app.goToCont().all();


        before.remove(modifyContact);
        before.add(contactData);
        Assert.assertEquals(before, after);
        assertThat(after, equalTo(before.withOut(modifyContact).withAdded(contactData)));


    }


}
