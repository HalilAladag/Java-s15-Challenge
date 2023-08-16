package com.librarysystem.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
    private Librarian librarian;
    private List<User> users;
    private List<Item> items;
    private Map<Integer, Author> authors;

    public Library(Librarian librarian) {
        this.librarian = librarian;
        this.users = new ArrayList<>();
        this.items = new ArrayList<>();
        this.authors = new HashMap<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addAuthor(Author author) {
        authors.put(author.getId(), author);
    }

    public Author getAuthorByID(int authorID) {
        return authors.get(authorID);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Item> getItems() {
        return items;
    }
    public List<Book> getBooksByCategory(String categoryName) {
        List<Book> booksInCategory = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Book) {
                Book book = (Book) item;
                if (book.getCategory().getName().equalsIgnoreCase(categoryName)) {
                    booksInCategory.add(book);
                }
            }
        }
        return booksInCategory;
    }

    public List<Book> getBooksByAuthor(String authorName) {
        List<Book> booksByAuthor = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Book) {
                Book book = (Book) item;
                if (book.getAuthor().getName().equalsIgnoreCase(authorName)) {
                    booksByAuthor.add(book);
                }
            }
        }
        return booksByAuthor;
    }
}
