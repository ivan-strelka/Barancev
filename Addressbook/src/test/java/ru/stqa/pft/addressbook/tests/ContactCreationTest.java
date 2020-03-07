package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase {

    @Test
    public void testCreationContact() {
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getNavigationHelper().goToContactPage();
        ContactData contactData = new ContactData("Fuller",
                "Brad2",
                "lazinywiqa@mailinator.com",
                "Consectetur lorem re",
                "aaa");
        app.getContactHelper().createContact(contactData, true);
        app.getNavigationHelper().goToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() + 1);

        contactData.setId(after.stream().max(Comparator.comparingInt(ContactData::getId)).get().getId());
        before.add(contactData);
        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);

    }


}