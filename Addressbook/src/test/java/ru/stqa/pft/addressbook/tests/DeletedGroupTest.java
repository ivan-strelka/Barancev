package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupDate;

public class DeletedGroupTest extends TestBase {

    @Test
    public void testDeletGroup() {
        app.getNavigationHelper().goToGroupPage();
        if (!app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupDate("aaa", "bbb", "ccc"));
        }
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().deletedSelectedGroups();
        app.getGroupHelper().returnToGroupPage();
    }


}
