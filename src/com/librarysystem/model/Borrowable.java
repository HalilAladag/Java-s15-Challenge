package com.librarysystem.model;

public interface Borrowable {
    void borrow(User user);
    void returnItem(User user);
}
