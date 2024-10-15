package com.books.models;

public class BookResponse extends Book {

    public BookResponse() {}

    public BookResponse(int id, String name, String author, String publication, String category, int pages, double price) {
        super(id, name, author, publication, category, pages, price);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
