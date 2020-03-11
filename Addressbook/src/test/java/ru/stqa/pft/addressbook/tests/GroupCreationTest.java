package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupDate;

import java.util.Comparator;
import java.util.List;

public class GroupCreationTest extends TestBase {

    @Test
    public void testGroupCreation() {
        app.goTo().GroupPage();
        List<GroupDate> before = app.group().list();
        GroupDate group = new GroupDate().withName("aaa");
        app.group().createGroup(group);
        List<GroupDate> after = app.group().list();
        Assert.assertEquals(after.size(), before.size() + 1);

        group.withId(after.stream().max(Comparator.comparingInt(o -> o.getId())).get().getId());
        before.add(group);
        Comparator<? super GroupDate> byId = Comparator.comparingInt(GroupDate::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);


    }


}
