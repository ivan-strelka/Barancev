package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class DeletedContactTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.goToCont().all().size() == 0) {
            app.goTo().ContactPage();
            app.goToCont().create(new ContactData().withFirstName("Fuller")
                    .withLastName("Brad2")
                    .withEmail("lazinywiqa@mailinator.com")
                    .withAddress("Consectetur lorem re")
                    .withGroup("aaa"), true);
        }
    }

    @Test
    public void testDeleteContact() {
        Set<ContactData> before = app.goToCont().all();
        ContactData deletedContact = before.iterator().next();
        app.goToCont().delete(deletedContact);
        app.isAlertPresentAccept();
        app.goTo().GroupPage();
        app.goTo().goToHomePage();
        Set<ContactData> after = app.goToCont().all();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(deletedContact);
        Assert.assertEquals(before, after);
    }


}
