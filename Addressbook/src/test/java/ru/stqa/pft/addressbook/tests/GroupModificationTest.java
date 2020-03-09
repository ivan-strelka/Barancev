package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupDate;

import java.util.Comparator;
import java.util.List;

public class GroupModificationTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.getNavigationHelper().goToGroupPage();
        if (!app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupDate("aaa", "bbb", "ccc"));
        }
    }

    @Test
    public void testModificationGroup() {

        List<GroupDate> before = app.getGroupHelper().getGroupList();
        int index = before.size() - 1;
        GroupDate groupDate = new GroupDate(before.get(index).getId(), "d", "d", "d");
        app.getGroupHelper().modifyGroup(index, groupDate);
        List<GroupDate> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(index);
        before.add(groupDate);
        Comparator<? super GroupDate> byId = Comparator.comparingInt(GroupDate::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);


    }


}
