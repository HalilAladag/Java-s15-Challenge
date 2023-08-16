package com.librarysystem.model;

public abstract class Item {
    private int id;
    private String title;
    private int publisherid;
    private int authorid;
    private String type;

    public int getPublisherid() {
        return publisherid;
    }

    public int getAuthorid() {
        return authorid;
    }

    public Item(int id, String title) {
        this.id = id;
        this.title = title;
        this.authorid = authorid;
        this.publisherid = publisherid;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}