package ru.avalon.javapp.devj120;

import java.io.Serializable;

public class Book implements Serializable {
    private final String bookCode;
    private String isbn;
    private String name;
    private String authors;
    private int publishYear;

    public Book(String bookCode, String isbn, String name, String authors, int publishYear) {
        if (bookCode == null || bookCode.isBlank())
            throw new IllegalArgumentException("Code can't be neither null, or blank string.");
        this.bookCode = bookCode;
        setIsbn(isbn);
        setName(name);
        setAuthors(authors);
        setPublishYear(publishYear);
    }

    public String getBookCode() {
        return bookCode;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if (isbn == null || isbn.isBlank())
            throw new IllegalArgumentException("ISBN can't be neither null, or blank string.");
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Book name can't be neither null, or blank string.");
        this.name = name;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        if (publishYear < 1454)
            throw new IllegalArgumentException("Publish year must be greater 1454 year.");
        this.publishYear = publishYear;
    }
}
