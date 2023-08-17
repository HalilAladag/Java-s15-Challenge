package com.librarysystem.model;

import java.util.*;

public class User extends Person {

    private String name;
    private int borrowedBooksCount;
    public ArrayList borrowedItems = new ArrayList();
    private Map<Book, Integer> bookRatings;
    private double fineAmount;
    private String password;

    public User() {
        super();


        this.borrowedBooksCount = 0;
        this.borrowedItems = new ArrayList();
        this.bookRatings = new HashMap<>();
        this.fineAmount = 0;
        this.password = password;
        this.name =  name;
    }

    public boolean canBorrow() {
        return borrowedBooksCount < 5;
    }

    //@Override
    public ArrayList getBorrowedBooks() {
        Iterator<Object> iterator = borrowedItems.iterator();
        while (iterator.hasNext()) {
            Object item = iterator.next();
            if (item instanceof Integer) {
                iterator.remove();
            }
        }
        ArrayList arrayList = new ArrayList(borrowedItems);
        return arrayList;
    }
    public List<Magazine> getBorrowedMagazines() {
        return new ArrayList<>(borrowedItems);
    }
    public void borrowBook(Book book) {
        if (!borrowedItems.contains(book) && canBorrow()) {
            borrowedItems.add(book.getId());
            borrowedBooksCount++;
        }
    }
    public void borrowMagazine(Magazine magazine) {
        if (!borrowedItems.contains(magazine) && canBorrow()) {
            borrowedItems.add(magazine.getId());
            borrowedBooksCount++;
        }
    }
    public void returnItem(Book book) {
        if (borrowedItems.contains(book)) {
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

//    public int getDaysBorrowed(Item item) {
//        return borrowedItems.getOrDefault(item, 0);
//    }

    public void payFine(double amount) {
        fineAmount -= amount;
        System.out.println("Ödenen Ceza Miktarı: " + amount);
    }


    public double getTotalFineAmount() {
        return fineAmount;
    }

    public boolean bookHasBorrowed(Book book) {
        return borrowedItems.contains(book);
    }
    public boolean magazineHasBorrowed(Magazine magazine) {
        return borrowedItems.contains(magazine);
    }


    public void returnItem(Magazine magazine) {
        if (borrowedItems.contains(magazine)) {
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

