package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactModificationTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.goToCont().all().size() == 0) {
            app.goTo().ContactPage();
            app.goToCont().create(new ContactData().withFirstName("Fuller")
                    .withLastName("Brad2")
                    .withEmail("lazinywiqa@mailinator.com")
                    .withAddress("Consectetur lorem re")
                    .withGroup("aaa"), true);
        }
    }

    @Test
    public void testModificationContact() {
        Set<ContactData> before = app.goToCont().all();
        ContactData modifyContact = before.iterator().next();
        ContactData contactData = new ContactData()
                .withId(modifyContact.getId())
                .withFirstName("Fuller2")
                .withLastName("Brad2")
                .withEmail("lazinywiqa@mailinator.com")
                .withAddress("Consectetur lorem re")
                .withGroup("aaa");
        app.goTo().goToHomePage();
        app.goToCont().modify(contactData);
        app.goTo().goToHomePage();
        Set<ContactData> after = app.goToCont().all();
        Assert.assertEquals(after.size(), before.size());

        before.remove(modifyContact);
        before.add(contactData);
        Assert.assertEquals(before, after);


    }


}
