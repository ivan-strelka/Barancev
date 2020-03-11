package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class DeletedContactTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.goToCont().list().size() == 0) {
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
        List<ContactData> before = app.goToCont().list();
        int index = before.size() - 1;
        app.goToCont().delete(index);
        app.isAlertPresentAccept();
        app.goTo().GroupPage();
        app.goTo().goToHomePage();
        List<ContactData> after = app.goToCont().list();
        Assert.assertEquals(after.size(), before.size() - 1);
        before.remove(index);
        Assert.assertEquals(before, after);
    }


}
