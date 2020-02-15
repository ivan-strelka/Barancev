package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class DeletedGroupTest extends TestBase {

    @Test
    public void testDeletGroup() {
        app.getNavigationHelper().goToGroupPage();
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().deletedSelectedGroups();
        app.getGroupHelper().returnToGroupPage();
    }


}
