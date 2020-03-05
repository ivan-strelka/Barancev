package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class DeletedContactTest extends TestBase {


    @Test
    public void testDeleteContact() {
        int before = app.getContactHelper().getContactCount();
        if (!app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().goToContactPage();
            app.getContactHelper().createContact(new ContactData("Fuller",
                    "Brad",
                    "lazinywiqa@mailinator.com",
                    "Consectetur lorem re",
                    "zzz"), true);
        }
        app.getContactHelper().chooseContact(0);
        app.getContactHelper().submitDeleteContact();
        app.isAlertPresentAccept();
        app.getNavigationHelper().goToGroupPage(); // нужен для обновления страницы, иначе падает
        app.getNavigationHelper().goToHomePage();
        int after = app.getContactHelper().getContactCount();
        System.out.println("after is " + after);
        System.out.println("before is " + before);
        Assert.assertEquals(after, before - 1);


    }


}
