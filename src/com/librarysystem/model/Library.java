package com.librarysystem.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
    private ArrayList librarians;
    private List<User> users;
    private ArrayList books;
    private ArrayList authors;
    private ArrayList magazines;

    public Library(int id) {
        this.librarians = new ArrayList<>();
        this.users = new ArrayList<>();
        this.books = new ArrayList<>();
        this.authors = new ArrayList<>();
        this.magazines = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }
    public void addLibrarian(Librarian librarian) {
        librarians.add(librarian);
    }
    /* public void addAuthor(Author author) {
        authors.put(author.getId(), author);
    }



    */
    public Author getAuthorByID(int authorID) {
        return (Author) authors.get(authorID);
    }
    public void addBook(Book book) {
        books.add(book);
    }
    public void addMagazine(Magazine magazine) {
        books.add(magazine);
    }
    public void removeBook(Book book) {
        books.remove(book);
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Book> getBooks() {
        return books;
    }
    public List<Magazine> getMagazines() {
        return magazines;
    }
    public List<Book> getBooksByCategory(String categoryName) {
        List<Book> booksInCategory = new ArrayList<>();
        for(Object item : books) {
            if (item instanceof Book) {
                Book b = (Book) item;
                if ((b.getCategory().getName().equalsIgnoreCase(categoryName))){
                    booksInCategory.add(b);
                }
            }
        }
        return booksInCategory;
    }

    public List<Magazine> getMagazinesByCategory(String categoryName) {
        List<Magazine> magazinesInCategory = new ArrayList<>();
        for (Object magazine : magazines) {
            if (magazine instanceof Magazine) {
                //Book book = (Book) item;
                if (!((Magazine) magazine).getMagazinename().equals(null)) {
                    magazinesInCategory.add((Magazine) magazine);
                }
            }
        }
        return magazinesInCategory;
    }
    public List<Book> getBooksByAuthor(String authorName) {
        List<Book> booksByAuthor = new ArrayList<>();
        for(Object item : books) {
            if (item instanceof Book) {
                Book b = (Book) item;
                String author = b.getAuthor().getTitle();
                if ((author.equals(authorName))){
                    booksByAuthor.add(b);
                }
            }
        }
        return booksByAuthor;
    }

    public Author getAuthorByAuthorName(String authorName) {
        Author authorInfo = null;
        for (Object item : authors) {
            if (item instanceof Author) {
                Author a = (Author) item;
                authorInfo = a;
        }
    }
        return authorInfo;
    }

    public List<Magazine> getMagazinesByAuthor(String authorName) {
        List<Magazine> magazinesByAuthor = new ArrayList<>();
        for (Object magazine : magazines) {
            if (magazine instanceof Magazine) {
                if (!((Magazine) magazine).getMagazinename().equals(null)) {
                    magazinesByAuthor.add((Magazine) magazine);
                }
            }
        }
        return magazinesByAuthor;
    }
}
