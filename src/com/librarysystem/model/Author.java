package com.librarysystem.model;

import java.util.ArrayList;
import java.util.List;

public class Author extends Item {
    private String name;
    private int id;
    private static List<Author> authors = new ArrayList<>();

    public Author(int id, String name) {
        super(id, name);
        this.id = id;
        this.name = name;
        authors.add(this);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public static List<Author> getAll() {
        return authors;
    }

    @Override
    public String toString() {
        return getName();
    }
}
