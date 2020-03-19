package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContact() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        list.add(new Object[]{new ContactData().withFirstName("firstName 1").withLastName("lastName 1").withGroup("aaa")});
        list.add(new Object[]{new ContactData().withFirstName("firstName 2").withLastName("lastName 2").withGroup("aaa")});
        list.add(new Object[]{new ContactData().withFirstName("firstName 3").withLastName("lastName 3").withGroup("aaa")});
        return list.iterator();
    }


    @Test(dataProvider = "validContact")
    public void testCreationContact(ContactData contact) {
        Contacts before = app.goToCont().all();
        app.goTo().ContactPage();
        File photo = new File("src/test/resources/testPhoto.png");
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