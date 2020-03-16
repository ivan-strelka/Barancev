package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTest extends TestBase {

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
    public void testContactAddress() {
        app.goTo().goToHomePage();
        ContactData contact = app.goToCont().all().iterator().next();
        ContactData contactInfFromEditForm = app.goToCont().infoFromEditForm(contact);
        assertThat(contact.getAddress(), equalTo(mergeAddress(contactInfFromEditForm)));
    }

    public String mergeAddress(ContactData contact) {
        return Arrays.asList(contact.getAddress())
                .stream().filter((s) -> !equals(""))
                .collect(Collectors.joining());

    }


}
