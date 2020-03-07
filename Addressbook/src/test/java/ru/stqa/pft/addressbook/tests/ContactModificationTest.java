package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
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
        ContactData contactData = new ContactData(
                before.get(before.size() - 1).getId(),
                "Fuller2",
                "Brad2",
                "lazinywiqa@mailinator.com2",
                "Consectetur lorem re",
                "aaa");
        app.getContactHelper().fillContactForm(contactData, false);
        app.getContactHelper().submitContcactModification();
        app.getNavigationHelper().goToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        before.add(contactData);
        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);


    }
}
