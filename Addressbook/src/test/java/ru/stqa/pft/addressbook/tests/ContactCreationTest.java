package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.HashSet;
import java.util.List;

public class ContactCreationTest extends TestBase {

    @Test
    public void testCreationContact() {
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getNavigationHelper().goToContactPage();
        ContactData contactData = new ContactData("Fuller",
                "Brad",
                "lazinywiqa@mailinator.com",
                "Consectetur lorem re",
                "aaa");
        app.getContactHelper().createContact(contactData, true);
        app.getNavigationHelper().goToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() + 1);

        int max = 0;
        for (ContactData c : after) {
            if (c.getId() > max) {
                max = c.getId();
            }
        }
        contactData.setId(max);
        before.add(contactData);
        Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));

    }


}