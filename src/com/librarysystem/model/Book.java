package com.librarysystem.model;

import java.util.Random;

public class Book extends Item implements Borrowable {
    private Author author;
    private Category category;
    private boolean borrowed;
    private int rating;

    public Book(int id, String title, Author author, Category category) {
        super(id, title);
        this.author = author;
        this.category = category;
        this.borrowed = false;
        this.rating = new Random().nextInt(6);
    }

    public int getRating() {
        return rating;
    }

    public Author getAuthor() {
        return author;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public void borrow(User user) {
        if (!borrowed && user.canBorrow() && !user.hasOverdueItems()) {
            borrowed = true;
            user.borrow(this);
            System.out.println(getTitle() + " ödünç alındı. Kullanıcı: " + user.getName());
        } else {
            System.out.println("Kitap ödünç alınamadı.");
        }
    }

    @Override
    public void returnItem(User user) {
        if (borrowed && user.hasBorrowed(this)) {
            borrowed = false;
            user.returnItem(this);
            int daysBorrowed = user.getDaysBorrowed(this);
            if (daysBorrowed > 7) {
                double fine = (daysBorrowed - 7) * 0.5;
                user.payFine(fine);
                System.out.println("Kitap geri iade edildi. " + fine + " tl ceza ödeyiniz.");
            } else {
                System.out.println("Kitap iade edildi.");
            }
        } else {
            System.out.println("Kitap iade edilmedi.");
        }
    }

    public void updateBookInfo(String title, Author author, Category category) {
        setTitle(title);
        this.author = author;
        this.category = category;
    }
}
