package ru.avalon.javapp.devj120;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BooksList {
    private static final String FILE_NAME = "books.dat";

    private final List<Book> books;
    private final Set<String> bookCodes;

    public BooksList() throws IOException, ClassNotFoundException {
        File f = new File(FILE_NAME);
        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                books = (List<Book>) ois.readObject();
                bookCodes = new HashSet<>();
                for (Book b : books) {
                    bookCodes.add(b.getBookCode());
                }
            }
        } else {
            books = new ArrayList<>();
            bookCodes = new HashSet<>();
        }

    }

    public Book get(int index) {
        return books.get(index);
    }

    public int getCount() {
        return books.size();
    }

    public void add(Book b) {
        if (bookCodes.contains(b.getBookCode()))
            throw new IllegalArgumentException("Library has a book with specified code already.");
        books.add(b);
        bookCodes.add(b.getBookCode());
    }

    public void remove(int index) {
        Book b = books.get(index);
        bookCodes.remove(b.getBookCode());
        books.remove(index);
    }

    public void save() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(books);
        }
    }
}
