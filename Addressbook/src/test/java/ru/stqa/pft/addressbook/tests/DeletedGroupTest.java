package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupDate;

import java.util.List;

public class DeletedGroupTest extends TestBase {


    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().GroupPage();
        if (app.group().list().size() == 0) {
            app.group().createGroup(new GroupDate().withName("aaa1").withHeader("bbb").withFooter("ccc"));
        }
    }

    @Test
    public void testDeleteGroup() {
        List<GroupDate> before = app.group().list();
        int index = before.size() - 1;
        app.group().delete(index);
        List<GroupDate> after = app.group().list();
        Assert.assertEquals(after.size(), before.size() - 1);
        before.remove(index);
        Assert.assertEquals(before, after);

    }




}
