package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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
        Contacts before = app.goToCont().all();
        ContactData deletedContact = before.iterator().next();
        app.goToCont().delete(deletedContact);
        app.isAlertPresentAccept();
        app.goTo().GroupPage();
        app.goTo().goToHomePage();
        Contacts after = app.goToCont().all();
        Assert.assertEquals(after.size(), before.size() - 1);

        assertThat(after, equalTo(before.withOut(deletedContact)));
    }


}
