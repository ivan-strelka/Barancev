package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTest extends TestBase {

    @Test
    public void testCreationContact() {
        int before = app.getContactHelper().getContactCount();
        app.getNavigationHelper().goToContactPage();
        app.getContactHelper().createContact(new ContactData("Fuller",
                "Brad",
                "lazinywiqa@mailinator.com",
                "Consectetur lorem re",
                "zzz"), true);
        app.getNavigationHelper().goToHomePage();
        int after = app.getContactHelper().getContactCount();
        Assert.assertEquals(after, before + 1);
    }


}