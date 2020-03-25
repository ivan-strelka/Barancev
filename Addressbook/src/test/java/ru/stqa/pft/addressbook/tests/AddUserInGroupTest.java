package ru.stqa.pft.addressbook.tests;

import org.hibernate.Session;
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
        Groups groups = app.db().groups();
        if (app.db().contacts().size() == 0) {
            app.goTo().ContactPage();
            app.goToCont().create(new ContactData().withFirstName((properties.getProperty("web.firstName")))
                    .withLastName(properties.getProperty("web.lastName"))
                    .withEmail(properties.getProperty("web.email"))
                    .withAddress(properties.getProperty("web.address"))
                    .inGroup(groups.iterator().next()), true);
        }

        if (app.db().groups().size() == 0) {
            app.goTo().GroupPage();
            app.group().createGroup(new GroupData().withName("aaa").withHeader("bbb").withFooter("ccc"));
        }

    }


    @Test
    public void addUserInGroupTest() {
        app.goTo().goToHomePage();
        Contacts contacts = app.db().contacts();
        ContactData selectedContact = contacts.iterator().next();
        Groups groups = app.db().groups();
        GroupData selectedGroup = groups.iterator().next();
        int selectedContactId = selectedContact.getId();
        Groups beforeGroup = selectContact(selectedContactId).getGroups();
        String groupName = selectedGroup.getName();
        app.goToCont().addContactToGroup(groupName, selectedContactId);
        Groups afterGroups = selectContact(selectedContactId).getGroups();
        assertThat(afterGroups,
                equalTo(beforeGroup.withAdded(selectedGroup)));

    }

    public ContactData selectContact(int contactId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Object result = session.createQuery("from ContactData where id =" + contactId).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return ((ContactData) result);
    }


}
