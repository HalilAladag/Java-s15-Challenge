package com.librarysystem.main;

import com.librarysystem.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Librarian librarian = new Librarian(1, "sessizOl");
        Library library = new Library(1);
        library.addLibrarian(librarian);
        Author author1 = new Author(1, "George Orwell");
        Author author2 = new Author(2, "Jose Saramago");
        Author author3 = new Author(3, "tester testson");


        Category category1 = new Category(1, "Distopik Roman");
        Category category2 = new Category(2, "Roman");
        Category category3 = new Category(3, "test");

        Publisher publisher1 = new Publisher(1, "Yayınevi", "Can Yayınları");
        Publisher publisher2 = new Publisher(1, "Yayınevi", "Can Yayınları");


        User user1 = new User();
        User user2 = new User();

        addUser(library, user1);
        addUser(library, user2);

        Book book1 = new Book(1, "1984", author1, category1, false, 5, "Can Yayınları");
        Book book2 = new Book(2, "Körlük", author2, category2, false, 4,"Can Yayınları");
        Book book3 = new Book(3, "test2", author3, category3, false, 1,"Can Yayınları");
        Book book4 = new Book(4, "test3", author3, category3, false, 1,"Can Yayınları");
        Book book5 = new Book(5, "test4", author3, category3, false, 1,"Can Yayınları");
        Book book6 = new Book(6, "test5", author3, category3, false, 2,"Can Yayınları");

        addBook(library, book1);
        addBook(library, book2);
        addBook(library, book3);
        addBook(library, book4);
        addBook(library, book5);
        addBook(library, book6);

        Magazine magazine1 = new Magazine(3, "Dergi", publisher1);
        addMagazine(magazine1);

        Scanner scanner = new Scanner(System.in);;

        System.out.print("Kullanıcı Adı: ");
        String username = scanner.nextLine();

        System.out.print("Şifre: ");
        //int userPassword = scanner.nextInt();
        String userPassword = scanner.next();
        scanner.nextLine();

        User currentUser = null;

        for (User user : library.getUsers()) {
            if (user.getName().equals(username) && user.getPassword().equals(userPassword)) {
                currentUser = user;
                break;
            }
        }

        if (currentUser == null) {
            System.out.println("Kullanıcı bulunamadı.");
            return;
        }

        System.out.println("Hoş geldiniz, " + currentUser.getName() + "! Lütfen yapmak istediğiniz işlemi tuşlayın.");

        while (true) {
            System.out.println("1. Kitapları Listele");
            System.out.println("2. Kitap İade Et");
            System.out.println("3. Kitap Al");
            System.out.println("4. Kitap Bilgilerini Güncelle");
            System.out.println("5. Yazarlara Göre Kitapları Listele");
            System.out.println("6. Kategorilere Göre Kitapları Listele");
            System.out.println("7. Çıkış");


            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    listBooks(library);
                    break;
                case 2:
                    returnBook(currentUser, library, scanner);
                    break;
                case 3:
                    borrowBook(currentUser, library, scanner);
                    break;
                case 4:
                    updateBook(currentUser, library, scanner);
                    break;
                case 5:
                    listBooksByAuthor(library, scanner);
                    break;
                case 6:
                    listBooksByCategory(library, scanner);
                    break;
                case 7:
                    System.out.println("Çıkış yapılıyor.");
                    return;
                default:
                    System.out.println("Geçersiz seçenek.");
            }
        }
    }

    private static void borrowBook(User user, Library library, Scanner scanner) {
        System.out.print("Almak istediğiniz kitap ID'sini giriniz: ");
        int bookID = scanner.nextInt();
        scanner.nextLine();
        Object item = library.getBooks().get(bookID);
        if (item instanceof Book) {
            Book book = (Book) item;
            if (user.canBorrow()) {
                user.borrowBook(book);
                user.borrowedItems.add(book);
                library.removeBook(book);
                System.out.println(book.getName() + " ödünç alındı.");
            } else {
                System.out.println("Ödünç alınamadı. Limitiniz dolu.");
            }
        } else {
            System.out.println("Geçersiz ID.");
        }
    }

    private static void returnBook(User user, Library library, Scanner scanner) {
        System.out.println("İade etmek istediğiniz kitaplar:");
        ArrayList borrowedItems = new ArrayList(user.getBorrowedBooks());
        borrowedItems.addAll(user.getBorrowedMagazines());

        for (int i = 0; i < borrowedItems.size(); i++) {
            Object item = borrowedItems.get(i);
            if (item instanceof Book) {
                Book book = (Book) item;
                System.out.println((i + 1) + ". " + book.getName());
            }
        }

        System.out.print("İade etmek istediğiniz kitap ID'sini girin: ");
        int bookNumber = scanner.nextInt();
        scanner.nextLine();

        if (bookNumber >= 1 && bookNumber <= borrowedItems.size()) {
            Book book = (Book) borrowedItems.get(bookNumber - 1);
            if (book instanceof Book && user.bookHasBorrowed((Book) book)) {
                System.out.print("Kitaba puan vermek istiyor musunuz? (Evet/Hayır): ");
                String ratingChoice = scanner.nextLine();

                if (ratingChoice.equalsIgnoreCase("Evet")) {
                    System.out.print("1 ile 5 arasında puan giriniz): ");
                    int rating = scanner.nextInt();
                    scanner.nextLine();

                    if (rating >= 1 && rating <= 5) {
                        book.setRating(rating);
                        System.out.println("Puanınız kaydedildi.");
                    } else {
                        System.out.println("Geçersiz puan değeri.");
                    }
                }

                user.returnItem((Book) book);
                library.addBook((Book) book);
                System.out.println(((Book) book).getName() + " kitabı iade edildi.");
            } else {
                System.out.println("Geçersiz seçenek veya kitap kullanıcıya ait değil.");
            }
        } else {
            System.out.println("Geçersiz kitap ID.");
        }
    }


    private static void updateBook(User user, Library library, Scanner scanner) {
        System.out.println("Güncellemek istediğiniz kitabın ID'sini girin: ");
        int bookID = scanner.nextInt();
        scanner.nextLine();

        Book book = library.getBooks().get(bookID);
        if (book instanceof Book) {
            if (user.bookHasBorrowed(book)) {
                System.out.println("Bu kitabı güncelleyemezsiniz. Kitap ödünç alınmış durumda.");
            } else {
                System.out.print("Yeni başlık: ");
                String newTitle = scanner.nextLine();

                System.out.print("Yeni yazar adı: ");
                String authorName = scanner.next();
                scanner.nextLine();
                Author newAuthor = library.getAuthorByAuthorName(authorName);

                Random random = new Random();
                int randomNumber = random.nextInt(101000);

                System.out.print("Yeni kategori adı: ");
                String newCategoryName = scanner.nextLine();
                Category newCategory = new Category(randomNumber, newCategoryName);

                book.updateBookInfo(newTitle, newAuthor, newCategory);
                System.out.println("Kitap bilgileri güncellendi.");
            }
        } else {
            System.out.println("Geçersiz ID.");
        }
    }

    private static void listBooks(Library library) {
        System.out.println("Kütüphanedeki kitaplar:");
        for (Book book : library.getBooks()) {
            System.out.println(book.toString());
        }
    }
    private static void listMagazines(Library library) {
        System.out.println("Kütüphanedeki kitaplar:");
        for (Magazine magazine : library.getMagazines()) {
            System.out.println(magazine.getMagazinename() + ". " + magazine.getTitle()  + ". " + magazine.isBorrowed());
        }
    }

    private static void listBooksByCategory(Library library, Scanner scanner) {
        System.out.print("Kategori adını giriniz: ");
        String categoryName = scanner.nextLine();
        List<Book> booksInCategory = library.getBooksByCategory(categoryName);
        List<Magazine> magazinesInCategory = library.getMagazinesByCategory(categoryName);

        System.out.println("Kategori '" + categoryName + "' için bulunan kitaplar:");
        for (Book book : booksInCategory) {
            System.out.println(book.getName());
        }
        for (Magazine magazine : magazinesInCategory) {
            System.out.println(magazine.getMagazinename() + ". " + magazine.getTitle()  + ". " + magazine.isBorrowed());
        }
    }

    public static List<Book> getBooksByAuthorId(int authorId, List<Book> bookList) {
        List<Book> result = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getAuthor().equals(authorId)) {
                result.add(book);
            }
        }
        return result;
    }
    private static void listBooksByAuthor(Library library, Scanner scanner) {
        System.out.print("Yazar adını giriniz: ");
        String authorName = scanner.nextLine();
        List<Book> booksByAuthor = library.getBooksByAuthor(authorName);

        System.out.println("Yazar '" + authorName + "' için bulunan kitaplar:");
        for (Book book : booksByAuthor) {
            System.out.println(book.getId() + ". " + book.getName() + " - " + book.getCategory().getName());
            System.out.println();
        }
    }

    private static void listMagazinesByAuthor(Library library, Scanner scanner) {
        System.out.print("Yazar adını giriniz: ");
        String authorName = scanner.nextLine();
        List<Magazine> magazinesByAuthor = library.getMagazinesByAuthor(authorName);

        System.out.println("Yazar '" + authorName + "' için bulunan kitaplar:");
        for (Magazine magazine : magazinesByAuthor) {
            System.out.println(magazine.getMagazinename() + ". " + magazine.getTitle()  + ". " + magazine.isBorrowed());
        }
    }
    public static void addBook(Library library, Book book) {
        library.addBook(book);
        System.out.println(book.getName() + " kütüphaneye eklendi.");
    }

    public static void addMagazine(Magazine magazine) {
        Library library = new Library(1);
        library.addMagazine(magazine);
        System.out.println(magazine.getTitle() + " kütüphaneye eklendi.");
    }

    public static void addUser(Library library, User user) {
        library.addUser(user);
        System.out.println(user.getName() + " kütüphane üyesi olarak kaydedildi.");
    }
}
