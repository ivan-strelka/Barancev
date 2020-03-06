package ru.stqa.pft.addressbook.model;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "ContactData{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", group='" + group + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(address, that.address) &&
                Objects.equals(group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, address, group);
    }
}
