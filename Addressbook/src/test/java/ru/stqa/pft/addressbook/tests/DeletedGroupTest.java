package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupDate;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeletedGroupTest extends TestBase {


    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().GroupPage();
        if (app.group().all().size() == 0) {
            app.group().createGroup(new GroupDate().withName("aaa1").withHeader("bbb").withFooter("ccc"));
        }
    }

    @Test
    public void testDeleteGroup() {
        Groups before = app.group().all();
        GroupDate deletedGroup = before.iterator().next();
        app.group().delete(deletedGroup);
        Groups after = app.group().all();
        Assert.assertEquals(after.size(), before.size() - 1);

        assertThat(after, equalTo(before.withOut(deletedGroup)));


    }


}
