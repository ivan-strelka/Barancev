package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

    @Test
    public void testModificationContact() {
        int before = app.getContactHelper().getContactCount();
        if (!app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().goToContactPage();
            app.getContactHelper().createContact(new ContactData("Fuller",
                    "Brad",
                    "lazinywiqa@mailinator.com",
                    "Consectetur lorem re",
                    "zzz"), true);
        }
        app.getNavigationHelper().goToHomePage();
        app.getContactHelper().initContactModification(before - 1);
        app.getContactHelper().fillContactForm(new ContactData(
                "Martin11111",
                "Luter22222",
                "martin@mailinator.com22222",
                "Life forever33333",
                null), false);
        app.getContactHelper().submitContcactModification();
        app.getNavigationHelper().goToHomePage();
        int after = app.getContactHelper().getContactCount();
        Assert.assertEquals(after, before);


    }
}
