package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactEmailTest extends TestBase {

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


}
