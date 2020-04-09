package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;

public class DeleteContactFromGroupTest extends TestBase {

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
    public void testContactRemoveFromGroup() {
        Groups groups = app.db().getGroups();
        GroupData groupInf = groups.iterator().next();
        app.goTo().goToHomePage();
        ContactData before = null;
        List<ContactData> beforeCnts = app.goToCont().getContactsList();
        ContactData added = beforeCnts.iterator().next();
        for (ContactData b : beforeCnts) {
            if (b.getId() == added.getId()) {
                before = b;
            }
        }

        if (groupInf.getContacts().contains(added.getId())) {
            app.goToCont().deletedGroup(added, groupInf, true);
        } else {
            app.goToCont().deletedGroup(added, groupInf, false);
        }
        ContactData after = null;
        List<ContactData> afterCnts = app.goToCont().getContactsList();
        for (ContactData a : afterCnts) {
            if (a.getId() == before.getId()) {
                after = a;
            }
        }

        if (after.getGroups().size() != (before.getGroups().size())) {
            Assert.assertEquals(after.getGroups().size(), before.getGroups().size() - 1);
        }

        if (!after.getGroups().equals(before.getGroups())) {
            Assert.assertEquals(after.getGroups(), before.getGroups().withOut(groupInf));
        } else {
            Assert.assertEquals(after.getGroups(), before.getGroups());
        }

    }


}
