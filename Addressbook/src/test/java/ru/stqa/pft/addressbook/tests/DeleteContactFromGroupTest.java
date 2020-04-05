package ru.stqa.pft.addressbook.tests;

import com.google.common.collect.Sets;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactFromGroupTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        Groups groups = app.db().groups();
        if (app.db().groups().size() == 0) {
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
        app.goTo().goToHomePage();
        List<ContactData> beforeContactList = app.goToCont().getContactsList();
        ContactData contactData = null;

        Iterator<ContactData> allContacts = app.db().contacts().iterator();

        while (allContacts.hasNext()) {
            contactData = allContacts.next();
            if (contactData.getGroups().size() > 0) {
                break;
            }
        }
        if (contactData != null && contactData.getGroups().size() > 0) {
            app.goToCont().selectGroupFromFilterByGroupId(contactData.getGroups().iterator().next().getId());
            app.goToCont().removeFromGroup(contactData);

            ContactData after = null;

            Iterator<ContactData> updatedContacts = app.db().contacts().iterator();

            while (updatedContacts.hasNext()) {
                after = updatedContacts.next();
                if (after.getId() == contactData.getId()) {
                    break;
                }
            }
            List<ContactData> afterContactList = app.goToCont().getContactsList();

            Set<GroupData> beforeContactGroups = Sets.difference(contactData.getGroups(), after.getGroups());

            Assert.assertEquals(contactData.getGroups().size() - 1, after.getGroups().size());
            assertThat(after.getGroups(),
                    equalTo(contactData.getGroups().withOut(beforeContactGroups.iterator().next())));


//    @Test
//    public void testContactRemoveFromGroup() {
//        app.goTo().goToHomePage();
//        Groups groups = app.db().groups();
//        Contacts before = app.db().contacts();
//        ContactData removedFromGroupContact = before.iterator().next();
//        if (removedFromGroupContact.getGroups().size() == 0) {
//            removedFromGroupContact = app.goToCont().addContactToGroup(removedFromGroupContact, groups.iterator().next());
//            app.goTo().goToHomePage();
//        }
//        Groups contactInGroupsBeforeDeleting = app.db().contactInGroup();
//        GroupData deletedGroup = contactInGroupsBeforeDeleting.iterator().next();
//        app.goTo().goToHomePage();
//        app.goToCont().removeContactFromGroup(removedFromGroupContact, deletedGroup);
//        app.goTo().goToHomePage();
//
//        assertThat(app.goToCont().count(), equalTo(before.size()));
//        Groups contactInGroupsAfterDeleting = app.db().contactInGroup();
//        assertThat(contactInGroupsAfterDeleting, equalTo(contactInGroupsBeforeDeleting.withOut(deletedGroup)));
//    }


//    @Test
//    public void deleteContactFromGroup() {
//        Contacts contacts = app.db().contacts();
//        ContactData selectedContact = contacts.iterator().next();
//        Groups groups = app.db().groups();
//        GroupData selectedGroup = groups.iterator().next();
//        String groupName = selectedGroup.getName();
//        int selectedContactId = selectedContact.getId();
//        Groups beforeGroups = selectContact(selectedContactId).getGroups();
//        boolean indicator = false;
//        try {
//            assertThat(selectedContact.getGroups(),
//                    Matchers.<GroupData>containsInAnyOrder(selectedGroup));
//            indicator = true;
//        } catch (AssertionError e) {
//            indicator = false;
//        }
//        if (indicator == false) {
//            app.goToCont().addContactToGroup(groupName, selectedContactId);
//        }
//        app.goTo().goToHomePage();
//        app.goToCont().deleteContactFromGroup(groupName, selectedContactId);
//        Groups afterGroups = selectContact(selectedContactId).getGroups();
//        assertThat(afterGroups,
//                equalTo(beforeGroups.withOut(selectedGroup)));
//
//    }
//
//    public ContactData selectContact(int contactId) {
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        Object result = session.createQuery("from ContactData where id =" + contactId).uniqueResult();
//        session.getTransaction().commit();
//        session.close();
//        return ((ContactData) result);
//    }
//

        }
    }
}