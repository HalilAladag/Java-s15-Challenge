package com.librarysystem.model;

import java.util.List;

public class Librarian extends Person {
    public Librarian(int id, String name) {
        super(id, name);
    }

    @Override
    public List<Item> getBorrowedItems() {
        return null;
    }

    public void addBook(Library library, Book book) {
        library.addItem(book);
        System.out.println(book.getTitle() + " kütüphaneye eklendi.");
    }

    public void addUser(Library library, User user) {
        library.addUser(user);
        System.out.println(user.getName() + " kütüphane üyesi olarak kaydedildi.");
    }
}
