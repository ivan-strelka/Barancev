package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class ContactData {
    private int id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String address;
    private final String group;

    public ContactData(String firstName, String lastName, String email, String address, String group) {
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.group = group;

    }

    public ContactData(int id, String firstName, String lastName, String email, String address, String group) {
        this.id = id;
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

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return id == that.id &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
