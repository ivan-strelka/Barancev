package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTest extends TestBase {

    @Test
    public void testCreationContact() {

        app.getNavigationHelper().goToContactPage();
        app.getContactHelper().createContact(new ContactData("Fuller",
                "Brad",
                "lazinywiqa@mailinator.com",
                "Consectetur lorem re",
                "zzz"), true);
        app.getNavigationHelper().goToHomePage();
    }


}