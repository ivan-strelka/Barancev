package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


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
        Contacts usersAll = app.db().contacts();
        Groups groupsAll = app.db().getGroups();

        ContactData userSelect = null;
        GroupData groupSelect = null;
        ContactData userAfter = null;

        for (ContactData currentUser : usersAll) {
            Groups groupsOfSelecteUser = currentUser.getGroups();
            if (groupsOfSelecteUser.size() != groupsAll.size()) {
                groupsAll.removeAll(groupsOfSelecteUser);
                groupSelect = groupsAll.iterator().next();
                userSelect = currentUser;
                break;
            }
        }
        if (groupSelect == null) {
            ContactData user = new ContactData().withFirstName("Test")
                    .withLastName("Testovich")
                    .withEmail("aaa@ddd.ru")
                    .withHomePhone("+123123123")
                    .withMobilePhone("+123333")
                    .withWorkPhone("+1276324");
            app.goToCont().create(user, true);
            Contacts userFirst = app.db().contacts();
            user.withId(userFirst.stream()
                    .mapToInt((g) -> (g).getId()).max().getAsInt());
            userSelect = user;
            groupSelect = groupsAll.iterator().next();
        }

        app.goTo().goToHomePage();
        app.goToCont().allGroupsOnPage();
        app.goToCont().selectGroup(userSelect, groupSelect);
        app.goTo().goToHomePage();

        Contacts usersAllAfter = app.db().contacts();
        for (ContactData currentUserAfter : usersAllAfter) {
            if (currentUserAfter.getId() == userSelect.getId()) {
                userAfter = currentUserAfter;
            }
        }

        assertThat(userSelect.getGroups(),
                equalTo(userAfter.getGroups().withOut(groupSelect)));
    }

}
