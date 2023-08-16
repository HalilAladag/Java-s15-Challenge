package com.librarysystem.main;

import com.librarysystem.model.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Librarian librarian = new Librarian(1, "sessizOl");
        Library library = new Library(librarian);

        Author author1 = new Author(1, "George Orwell");
        Author author2 = new Author(2, "Jose Saramago");

        Category category1 = new Category("Distopik");
        Category category2 = new Category("Edebiyat");

        Publisher publisher1 = new Publisher("Can Yayınları");
        Publisher publisher2 = new Publisher("Can Yayınları");

        User user1 = new User(1, "Halil", "Halil");
        User user2 = new User(2, "test2", "Halil");

        librarian.addUser(library, user1);
        librarian.addUser(library, user2);

        Book book1 = new Book(1, "1984", author1, category1);
        Book book2 = new Book(2, "Körlük", author2, category2);

        librarian.addBook(library, book1);
        librarian.addBook(library, book2);

        Magazine magazine1 = new Magazine(3, "Dergi", publisher1);

        library.addItem(magazine1);

        Scanner scanner = new Scanner(System.in);

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
        Item item = library.getItems().get(bookID);
        if (item instanceof Book) {
            Book book = (Book) item;
            if (user.canBorrow()) {
                user.borrow(book);
                library.removeItem(book);
                System.out.println(book.getTitle() + " ödünç alındı.");
            } else {
                System.out.println("Ödünç alınamadı. Limitiniz dolu.");
            }
        } else {
            System.out.println("Geçersiz ID.");
        }
    }

    private static void returnBook(User user, Library library, Scanner scanner) {
        System.out.println("İade etmek istediğiniz kitaplar:");
        List<Item> borrowedItems = user.getBorrowedItems();
        for (int i = 0; i < borrowedItems.size(); i++) {
            Item item = borrowedItems.get(i);
            System.out.println((i + 1) + ". " + item.getTitle());
        }
        System.out.print("İade etmek istediğiniz kitap ID'sini girin: ");
        int bookNumber = scanner.nextInt();
        scanner.nextLine();

        if (bookNumber >= 1 && bookNumber <= borrowedItems.size()) {
            Item item = borrowedItems.get(bookNumber - 1);
            if (item instanceof Book && user.hasBorrowed(item)) {
                Book book = (Book) item;
                user.returnItem(book);
                library.addItem(book);
                int daysBorrowed = user.getDaysBorrowed(book);
                if (daysBorrowed > 7) {
                    double fine = (daysBorrowed - 7) * 0.5;
                    user.payFine(fine);
                    System.out.println(book.getTitle() + " kitabı iade edildi. " + fine + " dolar ceza ödendi.");
                } else {
                    System.out.println(book.getTitle() + " kitabı iade edildi.");
                }
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

        Item item = library.getItems().get(bookID);
        if (item instanceof Book) {
            Book book = (Book) item;
            if (user.hasBorrowed(book)) {
                System.out.println("Bu kitabı güncelleyemezsiniz. Kitap ödünç alınmış durumda.");
            } else {
                System.out.print("Yeni başlık: ");
                String newTitle = scanner.nextLine();

                System.out.print("Yeni yazar ID: ");
                int authorID = scanner.nextInt();
                scanner.nextLine();
                Author newAuthor = library.getAuthorByID(authorID);

                System.out.print("Yeni kategori adı: ");
                String newCategoryName = scanner.nextLine();
                Category newCategory = new Category(newCategoryName);

                book.updateBookInfo(newTitle, newAuthor, newCategory);
                System.out.println("Kitap bilgileri güncellendi.");
            }
        } else {
            System.out.println("Geçersiz ID.");
        }
    }

    private static void listBooks(Library library) {
        System.out.println("Kütüphanedeki kitaplar:");
        for (Item item : library.getItems()) {
            System.out.println(item.getId() + ". " + item.getTitle());
        }
    }
    private static void listBooksByCategory(Library library, Scanner scanner) {
        System.out.print("Kategori adını giriniz: ");
        String categoryName = scanner.nextLine();
        List<Book> booksInCategory = library.getBooksByCategory(categoryName);

        System.out.println("Kategori '" + categoryName + "' için bulunan kitaplar:");
        for (Book book : booksInCategory) {
            System.out.println(book.getId() + ". " + book.getTitle() + " - " + book.getAuthor().getName());
        }
    }

    private static void listBooksByAuthor(Library library, Scanner scanner) {
        System.out.print("Yazar adını giriniz: ");
        String authorName = scanner.nextLine();
        List<Book> booksByAuthor = library.getBooksByAuthor(authorName);

        System.out.println("Yazar '" + authorName + "' için bulunan kitaplar:");
        for (Book book : booksByAuthor) {
            System.out.println(book.getId() + ". " + book.getTitle() + " - " + book.getCategory().getName());
        }
    }
}
