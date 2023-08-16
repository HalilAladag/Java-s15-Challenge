package com.librarysystem.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

    private String name;
    private int borrowedBooksCount;
    private Map<Item, Integer> borrowedItems;
    private Map<Book, Integer> bookRatings;
    private double fineAmount;
    private String password;

    public User(int id, String name,String password) {

        this.borrowedBooksCount = 0;
        this.borrowedItems = new HashMap<>();
        this.bookRatings = new HashMap<>();
        this.fineAmount = 0;
        this.password = password;
        this.name =  name;
    }

    public boolean canBorrow() {
        return borrowedBooksCount < 5;
    }

    //@Override
    public List<Item> getBorrowedItems() {
        return new ArrayList<>(borrowedItems.keySet());
    }

    public void borrow(Book book) {
        if (!borrowedItems.containsKey(book) && canBorrow()) {
            borrowedItems.put(book, 14);
            borrowedBooksCount++;
        }
    }

    public void returnItem(Book book) {
        if (borrowedItems.containsKey(book)) {
            borrowedItems.remove(book);
            borrowedBooksCount--;
        }
    }

    public void rateBook(Book book, int rating) {
        if (rating >= 0 && rating <= 5) {
            bookRatings.put(book, rating);
        }
    }

    public int getRating(Book book) {
        return bookRatings.getOrDefault(book, 0);
    }

    public int getDaysBorrowed(Item item) {
        return borrowedItems.getOrDefault(item, 0);
    }

    public void payFine(double amount) {
        fineAmount -= amount;
        System.out.println("Ödenen Ceza Miktarı: " + amount);
    }

    public boolean hasOverdueItems() {
        for (Map.Entry<Item, Integer> entry : borrowedItems.entrySet()) {
            if (entry.getValue() > 14) {
                return true;
            }
        }
        return false;
    }

    public double getTotalFineAmount() {
        return fineAmount;
    }

    public boolean hasBorrowed(Item item) {
        return borrowedItems.containsKey(item);
    }
    public void borrow(Magazine magazine) {
        if (!borrowedItems.containsKey(magazine) && canBorrow() && !hasOverdueItems()) {
            borrowedItems.put(magazine, 7); // 1 hafta ödünç alma süresi
            borrowedBooksCount++;
        }
    }

    public void returnItem(Magazine magazine) {
        if (borrowedItems.containsKey(magazine)) {
            borrowedItems.remove(magazine);
            borrowedBooksCount--;
        }
    }
    public String getName() {
        return name;
    }
    //public int getId() {
    //    return id;
    //}
    public String getPassword() {
        return password;
    }
}

