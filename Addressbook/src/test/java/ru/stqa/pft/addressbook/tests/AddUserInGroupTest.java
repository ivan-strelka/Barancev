package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;


public class AddUserInGroupTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        Groups groups = app.db().getGroups();
        if (app.db().getGroups().size() == 0) {
            app.goTo().GroupPage();
            app.group().createGroup(new GroupData().withName("aaa").withHeader("bbb").withFooter("ccc"));
        }

        if (app.db().contacts().size() == 0) {
            app.goTo().addContactPage();
            app.goToCont().create(new ContactData().withFirstName((properties.getProperty("web.firstName")))
                    .withLastName(properties.getProperty("web.lastName"))
                    .withEmail(properties.getProperty("web.email"))
                    .withAddress(properties.getProperty("web.address"))
                    .inGroup(groups.iterator().next()), true);
        }
    }


    @Test
    public void testAddUserToGroup() {
        app.goTo().goToHomePage();

        ContactData before = null;
        List<ContactData> beforeCnts = app.goToCont().getContactsList();
        ContactData added = null;

        int score = 0;
        Groups groupsBD = app.db().getGroups();
        int allGroup = groupsBD.size();
        for (ContactData a : beforeCnts) {
            if (a.getGroups().size() != allGroup) {
                groupsBD.removeAll(a.getGroups());
                added = a;
                score++;
                break;
            }
        }

        if (score == 0) {
            app.goTo().GroupPage();
            app.group().createGroup(new GroupData()
                    .withName("aaa")
                    .withFooter("ccc")
                    .withHeader("bbb"));
            app.goTo().goToHomePage();
            groupsBD = app.db().getGroups();
            allGroup = groupsBD.size();
            for (ContactData a : beforeCnts) {
                if (a.getGroups().size() != allGroup) {
                    added = a;
                    break;
                }
            }
        }

        GroupData groupFinish = groupsBD.iterator().next();
        for (ContactData b : beforeCnts) {
            if (b.getId() == added.getId()) {
                before = b;
            }
        }
        app.goToCont().addInGroup(added, groupFinish);
        ContactData after = null;
        List<ContactData> afterCnts = app.goToCont().getContactsList();
        for (ContactData a : afterCnts) {
            if (a.getId() == before.getId()) {
                after = a;
            }
        }

        if (after.getGroups().size() != before.getGroups().size()) {
            Assert.assertEquals(after.getGroups().size(), before.getGroups().size() + 1);
        }

        if (!after.getGroups().equals(before.getGroups())) {
            Assert.assertEquals(after.getGroups(), before.getGroups().withAdded(groupFinish));
        } else {
            Assert.assertEquals(after.getGroups(), before.getGroups());
        }
    }

}
