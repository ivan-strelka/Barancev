package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactCreationTest extends TestBase {

    @Test
    public void testCreationContact() {
        Set<ContactData> before = app.goToCont().all();
        app.goTo().ContactPage();
        ContactData contactData = new ContactData().withFirstName("Fuller")
                .withLastName("Brad2")
                .withEmail("lazinywiqa@mailinator.com")
                .withAddress("Consectetur lorem re")
                .withGroup("aaa");
        app.goToCont().create(contactData, true);
        app.goTo().goToHomePage();
        Set<ContactData> after = app.goToCont().all();
        Assert.assertEquals(after.size(), before.size() + 1);

        contactData.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
        before.add(contactData);
        Assert.assertEquals(before, after);

    }


}