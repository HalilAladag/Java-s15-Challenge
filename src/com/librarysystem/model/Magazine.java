package com.librarysystem.model;

public class Magazine extends Item implements Borrowable {
    private Publisher publisher;
    private boolean borrowed;

    public Magazine(int id, String title, Publisher publisher) {
        super(id, title);
        this.publisher = publisher;
        this.borrowed = false;
    }

    @Override
    public void borrow(User user) {
        if (!borrowed && user.canBorrow() && !user.hasOverdueItems()) {
            borrowed = true;
            user.borrow(this);
            System.out.println(getTitle() + " dergisi ödünç alındı. Kullanıcı: " + user.getName());
        } else {
            System.out.println("Dergi ödünç alınamadı.");
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
                System.out.println(getTitle() + " dergisi geri iade edildi. " + fine + " dolar ceza ödendi.");
            } else {
                System.out.println(getTitle() + " dergisi geri iade edildi.");
            }
        } else {
            System.out.println("Dergi geri iade edilemedi.");
        }
    }
}
