package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupDate;

import java.util.List;

public class DeletedGroupTest extends TestBase {


    @BeforeMethod
    public void ensurePreconditions() {
        app.getNavigationHelper().goToGroupPage();
        if (!app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupDate("aaa", "bbb", "ccc"));
        }
    }

    @Test
    public void testDeleteGroup() {
        List<GroupDate> before = app.getGroupHelper().getGroupList();
        app.getGroupHelper().selectGroup(before.size() - 1);
        app.getGroupHelper().deletedSelectedGroups();
        app.getGroupHelper().returnToGroupPage();
        List<GroupDate> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size() - 1);
        before.remove(before.size() - 1);
        Assert.assertEquals(before, after);

    }


}
