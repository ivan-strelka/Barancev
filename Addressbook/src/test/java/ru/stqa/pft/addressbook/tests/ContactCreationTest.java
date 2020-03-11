package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {

    @Test
    public void testCreationContact() {
        Contacts before = app.goToCont().all();
        app.goTo().ContactPage();
        ContactData contact = new ContactData().withFirstName("Fuller")
                .withLastName("Brad2")
                .withEmail("lazinywiqa@mailinator.com")
                .withAddress("Consectetur lorem re")
                .withGroup("aaa");
        app.goToCont().create(contact, true);
        app.goTo().goToHomePage();
        Contacts after = app.goToCont().all();
        assertThat(after.size(), equalTo(before.size() + 1));

        assertThat(after, equalTo(before.withAdded(contact.withId(after
                .stream().mapToInt((c) -> c.getId()).max().getAsInt()))));

    }

}