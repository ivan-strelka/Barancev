package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class GroupData {
    private int id = Integer.MAX_VALUE;
    private String name;
    private String header;
    private String footer;

    public int getId() {
        return id;
    }

    public GroupData withId(int id) {
        this.id = id;
        return this;
    }

    public GroupData withName(String name) {
        this.name = name;
        return this;
    }

    public GroupData withHeader(String header) {
        this.header = header;
        return this;
    }

    public GroupData withFooter(String footer) {
        this.footer = footer;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getHeader() {

        return header;
    }

    public String getFooter() {

        return footer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupData groupDate = (GroupData) o;
        return id == groupDate.id &&
                Objects.equals(name, groupDate.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "GroupDate{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }


}