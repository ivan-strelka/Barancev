package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeletedGroupTest extends TestBase {


    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().GroupPage();
        if (app.group().all().size() == 0) {
            app.group().createGroup(new GroupData()
                    .withName(properties.getProperty("web.groupName"))
                    .withHeader(properties.getProperty("web.groupHeader"))
                    .withFooter(properties.getProperty("web.groupFooter")));
        }
    }

    @Test
    public void testDeleteGroup() {
        Groups before = app.group().all();
        GroupData deletedGroup = before.iterator().next();
        app.group().delete(deletedGroup);
        assertThat(app.group().Count(), equalTo(before.size() - 1));
        Groups after = app.group().all();

        assertThat(after, equalTo(before.withOut(deletedGroup)));


    }


}
