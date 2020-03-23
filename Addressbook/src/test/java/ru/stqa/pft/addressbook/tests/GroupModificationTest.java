package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().GroupPage();
            app.group().createGroup(new GroupData().withName("aaa").withHeader("bbb").withFooter("ccc"));
        }

    }

    @Test
    public void testModificationGroup() throws IOException {
        properties = new Properties();
        properties.load(new FileReader(new File(String.format("src/test/resources/local.properties"))));
        Groups before = app.db().groups();
        GroupData modifyGroup = before.iterator().next();
        GroupData groupDate = new GroupData()
                .withId(modifyGroup.getId())
                .withName(properties.getProperty("web.groupNameMOD"))
                .withHeader(properties.getProperty("web.groupHeaderMOD"))
                .withFooter(properties.getProperty("web.groupFooterMOD"));
        app.goTo().GroupPage();
        app.group().modify(groupDate);
        assertThat(app.group().Count(), equalTo(before.size()));
        Groups after = app.db().groups();

        assertThat(after, equalTo(before.withOut(modifyGroup).withAdded(groupDate)));
    }


}
