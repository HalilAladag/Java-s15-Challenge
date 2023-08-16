package com.librarysystem.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Map;
import java.util.List;

public class Book extends Category {
    private int id;
    private Author author;
    private Category category;
    private boolean borrowed;
    private int rating;

    public Book(int id, String title, Author author, Category category, boolean borrowed, int rating) {
        super(id, title);
        this.author = author;
        this.category = category;
        this.borrowed = borrowed;
        this.rating = rating;
        this.id = id;
    }

//    Category(int id, String title, Author author, Category category) {
//        super(id, title);
//        this.author = author;
//        this.category = category;
//        this.borrowed = false;
//        this.rating = new Random().nextInt(6);
//    }

    public int getRating() {
        return rating;
    }
    public int getId() {
        return id;
    }
    public Author getAuthor() {
        return author;
    }

    public Category getCategory() {
        return category;
    }
    private ArrayList borrowedItems = new ArrayList();
    public List<Item> getBorrowedItems() {

            return new ArrayList<>(borrowedItems);
        }
        /*public boolean hasOverdueItems() {
        for (Map.Entry<Item, Integer> entry : borrowedItems.values()) {
            if (entry.getValue() > 14) {
                return true;
            }
        }
        return false;
    }
    */

    public void borrow(User user) {
        //if (!borrowed && user.canBorrow() && !hasOverdueItems()) {
        if (!borrowed && user.canBorrow()) {
            borrowed = true;
            user.borrowBook(this);
            System.out.println(getName() + " ödünç alındı. Kullanıcı: " + user.getName());
        } else {
            System.out.println("Kitap ödünç alınamadı.");
        }
    }

    public void returnItem(User user) {
        if (borrowed && user.bookHasBorrowed(this)) {
            borrowed = false;
            //int daysBorrowed = user.getDaysBorrowed(this);
//            if (daysBorrowed > 7) {
//                double fine = (daysBorrowed - 7) * 0.5;
//                user.payFine(fine);
//                System.out.println("Kitap geri iade edildi. " + fine + " tl ceza ödeyiniz.");
//            } else {
//                System.out.println("Kitap iade edildi.");
//            }
            System.out.println("Kitap iade edildi.");
        } else {
            System.out.println("Kitap iade edilmedi.");
        }
    }

    public void updateBookInfo(String title, Author author, Category category) {
        this.author = author;
        this.category = category;
    }

    private boolean borrowed() {
        return false;
    }
    private static void listBooks(Library library) {
        System.out.println("Kütüphanedeki kitaplar:");
        for (Book book : library.getBooks()) {
            System.out.println(book);
        }
    }
    @Override
    public String toString() {
        return "Kitap : "+ getName() + "| "
                +"Yazar: " + getAuthor().getName() + "| "
                + "Kategori: "+getCategory().getName() + "| "
                + "Puan:  " + getRating()+ "| "+ "Id: "+ getId();
    }

}

