package ru.avalon.javapp.devj120;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BooksList {
    private final List<Book> books = new ArrayList<>();
    private final Set<String > bookCodes = new HashSet<>();

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
}
