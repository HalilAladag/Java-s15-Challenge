package com.librarysystem.model;

import java.util.List;

public class Author extends Person {

    private String name;
    private int id;

    public Author(int id, String name, String name1) {
        super(id, name);
        this.name = name1;
        this.id = id;
    }

    public Author(int id, String name) {
        super(id, name);
    }

    @Override
    public List<Item> getBorrowedItems() {
        return null;
    }
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
}
