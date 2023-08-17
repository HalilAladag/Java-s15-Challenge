package com.librarysystem.model;

public class Category extends Library {
    private String name;
    private int id;


    public Category(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

}
