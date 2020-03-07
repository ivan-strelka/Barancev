package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class GroupDate {
    private int id;
    private final String name;
    private final String header;
    private final String footer;

    public GroupDate(int id, String name, String header, String footer) {
        this.id = id;
        this.name = name;
        this.header = header;
        this.footer = footer;

    }

    public GroupDate(String name, String header, String footer) {
        this.id = Integer.MAX_VALUE;
        this.name = name;
        this.header = header;
        this.footer = footer;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        GroupDate groupDate = (GroupDate) o;
        return Objects.equals(name, groupDate.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "GroupDate{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }


}
