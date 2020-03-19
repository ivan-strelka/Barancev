package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.csv"));
        String line = reader.readLine();
        while (line != null) {
            String[] split = line.split(";");
            list.add(new Object[]{new ContactData().withFirstName(split[0]).withLastName(split[1]).withGroup(split[2])});
            line = reader.readLine();
        }
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