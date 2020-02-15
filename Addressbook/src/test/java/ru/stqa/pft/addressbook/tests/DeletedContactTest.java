package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class DeletedContactTest extends TestBase {


    @Test
    public void testDeleteContact() {
        app.getContactHelper().chooseContact();
        app.getContactHelper().submitDeleteContact();
        app.isAlertPresentAccept();
        app.getNavigationHelper().goToHomePage();


    }


}
