package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
        ContactData userAfter = null;
        ContactData userSelect;
        GroupData groupSelect = null;

        Groups groups = app.db().getGroups();
        Contacts users = app.db().contacts();
        app.goTo().goToHomePage();
        userSelect = users.iterator().next();

        for (ContactData currentUser : users) {
            Groups currentGroup = currentUser.getGroups();
            if (currentGroup.size() > 0) {
                userSelect = currentUser;
                groupSelect = currentUser.getGroups().iterator().next();
                break;
            }
        }

        if (userSelect.getGroups().size() == 0) {
            groupSelect = groups.iterator().next();
            app.goToCont().selectGroup(userSelect, groupSelect);
        }

        app.goToCont().usersInGroup(groupSelect);
        app.goToCont().editContactById(userSelect.getId());
        app.goToCont().removeUserFromGroup();
        app.goTo().goToHomePage();

        Contacts usersAllAfter = app.db().contacts();
        for (ContactData userChoiceAfter : usersAllAfter) {
            if (userChoiceAfter.getId() == userSelect.getId()) {
                userAfter = userChoiceAfter;
            }
        }

        assertThat(userSelect.getGroups(),
                equalTo(userAfter.getGroups().withAdded(groupSelect)));
    }


}
