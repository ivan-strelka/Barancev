package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTest extends TestBase {

    @Test
    public void testGroupCreation() {
        app.goTo().GroupPage();
        Groups before = app.group().all();
        GroupData group = new GroupData().withName("aaa");
        app.group().createGroup(group);
        assertThat(app.group().Count(), equalTo(before.size() + 1));
        Groups after = app.group().all();


        group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        assertThat(after, equalTo(
                before.withAdded(group.withId(after.stream()
                        .mapToInt((g) -> g.getId()).max().getAsInt()))));

    }

    @Test
    public void testBadGroupCreation() {
        app.goTo().GroupPage();
        Groups before = app.group().all();
        GroupData group = new GroupData().withName("'");
        app.group().createGroup(group);
        assertThat(app.group().Count(), equalTo(before.size()));
        Groups after = app.group().all();

        group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        assertThat(after, equalTo(before));

    }


}
