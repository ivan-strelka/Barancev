package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class DeletedContactTest extends TestBase {


    @Test
    public void testDeleteContact() {
        if (!app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().goToContactPage();
            app.getContactHelper().createContact(new ContactData("Fuller",
                    "Brad",
                    "lazinywiqa@mailinator.com",
                    "Consectetur lorem re",
                    "zzz"), true);
        }
        app.getNavigationHelper().goToHomePage();
        app.getContactHelper().chooseContact();
        app.getContactHelper().submitDeleteContact();
        app.isAlertPresentAccept();
        app.getNavigationHelper().goToHomePage();


    }


}
