package com.librarysystem.model;

public class Publisher extends Item {
    private String name;
    private int id;

    public Publisher(int id, String title, String name) {
        super(id, title);
        this.name = name;
        this.id = id;
    }


    public String getName() {
        return name;
    }
}
