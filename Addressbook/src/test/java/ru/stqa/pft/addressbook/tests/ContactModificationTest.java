package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

    @Test
    public void testModificationContact() {
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData(
                "Martin11111",
                "Luter22222",
                "martin@mailinator.com22222",
                "Life forever33333"));
        app.getContactHelper().submitContcactModification();
        app.getNavigationHelper().goToHomePage();


    }
}
