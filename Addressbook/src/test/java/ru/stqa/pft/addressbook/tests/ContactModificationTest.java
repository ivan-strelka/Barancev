package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactModificationTest extends TestBase {

    @Test
    public void testModificationContact() {
        List<ContactData> before = app.getContactHelper().getContactList();
        if (!app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().goToContactPage();
            app.getContactHelper().createContact(new ContactData(
                    "Fuller",
                    "Brad",
                    "lazinywiqa@mailinator.com",
                    "Consectetur lorem re",
                    "aaa"), true);
        }
        app.getNavigationHelper().goToHomePage();
        app.getContactHelper().initContactModification(before.size() - 1);
        app.getContactHelper().fillContactForm(new ContactData(
                "Fuller",
                "Brad",
                "lazinywiqa@mailinator.com",
                "Consectetur lorem re",
                "aaa"), false);
        app.getContactHelper().submitContcactModification();
        app.getNavigationHelper().goToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());


    }
}
