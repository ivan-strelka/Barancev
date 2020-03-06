package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class DeletedContactTest extends TestBase {


    @Test
    public void testDeleteContact() {
        List<ContactData> before = app.getContactHelper().getContactList();
        if (!app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().goToContactPage();
            app.getContactHelper().createContact(new ContactData("Fuller",
                    "Brad",
                    "lazinywiqa@mailinator.com",
                    "Consectetur lorem re",
                    "aaa"), true);
        }
        app.getContactHelper().chooseContact(0);
        app.getContactHelper().submitDeleteContact();
        app.isAlertPresentAccept();
        app.getNavigationHelper().goToGroupPage(); // нужен для обновления страницы, иначе падает
        app.getNavigationHelper().goToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() - 1);
        System.out.println("AFTER IS " + after.size());
        System.out.println("BEFORE IS " + before.size());
        before.remove(before.size() - 1);
        System.out.println("AFTER IS " + after.size());
        System.out.println("BEFORE IS " + before.size());
        Assert.assertEquals(before, after);


    }


}
