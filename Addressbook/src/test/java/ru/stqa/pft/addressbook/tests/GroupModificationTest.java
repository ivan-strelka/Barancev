package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupDate;

import java.util.Set;

public class GroupModificationTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().GroupPage();
        if (app.group().all().size() == 0) {
            app.group().createGroup(new GroupDate().withName("aaa").withHeader("bbb").withFooter("ccc"));
        }
    }

    @Test
    public void testModificationGroup() {

        Set<GroupDate> before = app.group().all();
        GroupDate modifyGroup = before.iterator().next();
        GroupDate groupDate = new GroupDate()
                .withId(modifyGroup.getId())
                .withName("d")
                .withHeader("d")
                .withFooter("d");
        app.group().modify(groupDate);
        Set<GroupDate> after = app.group().all();
        Assert.assertEquals(after.size(), before.size());

        before.remove(modifyGroup);
        before.add(groupDate);
        Assert.assertEquals(before, after);
    }


}
