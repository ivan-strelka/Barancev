package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContactFromXML() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.xml"));
        String xml = "";
        String line = reader.readLine();
        while (line != null) {
            xml += line;
            line = reader.readLine();
        }
        XStream xstream = new XStream();
        xstream.processAnnotations(ContactData.class);
        List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
        return contacts.stream().map((c) -> new Object[]{c})
                .collect(Collectors.toList())
                .iterator();
    }

    @DataProvider
    public Iterator<Object[]> validContactFromJSON() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.json"));
        String json = "";
        String line = reader.readLine();
        while (line != null) {
            json += line;
            line = reader.readLine();
        }
        Gson gson = new Gson();
        List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
        }.getType());
        return contacts.stream().map((g) -> new Object[]{g})
                .collect(Collectors.toList())
                .iterator();
    }


    @Test(dataProvider = "validContactFromJSON")
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