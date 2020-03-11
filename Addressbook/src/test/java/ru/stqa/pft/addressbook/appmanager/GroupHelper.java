package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupDate;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;

public class GroupHelper extends HelperBase {


    public GroupHelper(WebDriver wd) {
        super(wd);
    }

    public void returnToGroupPage() {
        click(By.linkText("group page"));
    }

    public void submitGroupCreation() {
        click(By.name("submit"));
    }

    public void fillGroupForm(GroupDate groupDate) {
        type(By.name("group_name"), groupDate.getName());
        type(By.name("group_header"), groupDate.getHeader());
        type(By.name("group_footer"), groupDate.getFooter());
    }

    public void initGroupCreation() {
        click(By.name("new"));
    }

    public void deletedSelectedGroups() {
        click(By.name("delete"));
    }

    public void selectGroupById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void initGroupModification() {
        click(By.name("edit"));
    }

    public void submitGroupModification() {
        click(By.xpath("//input[@name='update']"));
    }

    public void createGroup(GroupDate group) {
        initGroupCreation();
        fillGroupForm(group);
        submitGroupCreation();
        returnToGroupPage();

    }

    public void modify(GroupDate groupDate) {
        selectGroupById(groupDate.getId());
        initGroupModification();
        fillGroupForm(groupDate);
        submitGroupModification();
        returnToGroupPage();
    }

    public boolean isThereAGroup() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getGroupCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public void delete(GroupDate group) {
        selectGroupById(group.getId());
        deletedSelectedGroups();
        returnToGroupPage();
    }

    public Groups all() {
        Groups groups = new Groups();
        List<WebElement> elements = wd.findElements(By.xpath("//span[@class='group']"));
        for (WebElement element : elements) {
            String name = element.getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            groups.add(new GroupDate().withId(id).withName(name));
        }

        return groups;
    }


}
