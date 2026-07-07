package com.library.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository class responsible for data access operations on Book records.
 *
 * EX6: @Repository marks this class for component scanning and enables
 * Spring's persistence exception translation.
 */
@Repository  // EX6 — component scanning picks this up as a Spring-managed bean
public class BookRepository {

    // In-memory store simulating a database table
    private final List<String> books = new ArrayList<>();

    public BookRepository() {
        // Pre-populate with sample data
        books.add("The Great Gatsby");
        books.add("To Kill a Mockingbird");
        books.add("1984");
        System.out.println("BookRepository initialised by Spring.");
    }

    /**
     * Returns all books available in the repository.
     */
    public List<String> findAllBooks() {
        return books;
    }

    /**
     * Persists a new book title.
     */
    public void save(String bookTitle) {
        books.add(bookTitle);
        System.out.println("Book saved: " + bookTitle);
    }
}
