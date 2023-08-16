package com.librarysystem.model;

import java.util.List;

public abstract class Person {
    private int id;
    private String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }
/*
    public abstract List<Item> getBorrowedItems();*/
}