package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupDate;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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

        Groups before = app.group().all();
        GroupDate modifyGroup = before.iterator().next();
        GroupDate groupDate = new GroupDate()
                .withId(modifyGroup.getId())
                .withName("d")
                .withHeader("d")
                .withFooter("d");
        app.group().modify(groupDate);
        Groups after = app.group().all();
        Assert.assertEquals(after.size(), before.size());

        assertThat(after, equalTo(before.withOut(modifyGroup).withAdded(groupDate)));
    }


}
