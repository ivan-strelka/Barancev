package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.goToCont().list().size() == 0) {
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
        List<ContactData> before = app.goToCont().list();
        int index = before.size() - 1;
        ContactData contactData = new ContactData().withId(before.get(index).getId())
                .withFirstName("Fuller2")
                .withLastName("Brad2")
                .withEmail("lazinywiqa@mailinator.com")
                .withAddress("Consectetur lorem re")
                .withGroup("aaa");
        app.goTo().goToHomePage();
        app.goToCont().modify(index, contactData);
        app.goTo().goToHomePage();
        List<ContactData> after = app.goToCont().list();
        Assert.assertEquals(after.size(), before.size());

        before.remove(index);
        before.add(contactData);
        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);


    }


}
