package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase {

    @Test
    public void testCreationContact() {
        List<ContactData> before = app.goToCont().list();
        app.goTo().ContactPage();
        ContactData contactData = new ContactData("Fuller",
                "Brad2",
                "lazinywiqa@mailinator.com",
                "Consectetur lorem re",
                "aaa");
        app.goToCont().create(contactData, true);
        app.goTo().goToHomePage();
        List<ContactData> after = app.goToCont().list();
        Assert.assertEquals(after.size(), before.size() + 1);

        contactData.setId(after.stream().max(Comparator.comparingInt(ContactData::getId)).get().getId());
        before.add(contactData);
        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);

    }


}