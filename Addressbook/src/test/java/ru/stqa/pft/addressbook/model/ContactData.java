package ru.stqa.pft.addressbook.model;

public class ContactData {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String address;
    private final String group;

    public ContactData(String firstName, String lastName, String email, String address, String group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.group = group;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getGroup() {
        return group;
    }

    public String getLastName() {
        return lastName;
    }


    public String getEmail() {

        return email;
    }

    public String getAddress() {

        return address;
    }
}
