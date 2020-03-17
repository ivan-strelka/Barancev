package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {

    @Test
    public void testCreationContact() {
        Contacts before = app.goToCont().all();
        app.goTo().ContactPage();
        File photo = new File("src/test/resources/testPhoto.png");
        ContactData contact = new ContactData().withFirstName("Fuller")
                .withLastName("Brad2")
                .withEmail("lazinywiqa@mailinator.com")
                .withAddress("Consectetur lorem re")
                .withPhoto(photo)
                .withGroup("aaa");
        app.goToCont().create(contact, true);
        app.goTo().goToHomePage();
        assertThat(app.goToCont().Count(), equalTo(before.size() + 1));
        Contacts after = app.goToCont().all();


        assertThat(after, equalTo(before.withAdded(contact.withId(after
                .stream().mapToInt((c) -> c.getId()).max().getAsInt()))));

    }


    @Test
    public void testBadCreationContact() {
        Contacts before = app.goToCont().all();
        app.goTo().ContactPage();
        ContactData contact = new ContactData().withFirstName("'")
                .withLastName("")
                .withEmail("")
                .withAddress("")
                .withGroup("aaa");
        app.goToCont().create(contact, true);
        app.goTo().goToHomePage();
        assertThat(app.goToCont().Count(), equalTo(before.size()));
        Contacts after = app.goToCont().all();

        assertThat(after, equalTo(before));

    }


}