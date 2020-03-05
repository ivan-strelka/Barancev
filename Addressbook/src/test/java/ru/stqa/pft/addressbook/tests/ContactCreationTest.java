package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactCreationTest extends TestBase {

    @Test
    public void testCreationContact() {
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getNavigationHelper().goToContactPage();
        app.getContactHelper().createContact(new ContactData("Fuller",
                "Brad",
                "lazinywiqa@mailinator.com",
                "Consectetur lorem re",
                "zzz"), true);
        app.getNavigationHelper().goToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() + 1);
    }


}