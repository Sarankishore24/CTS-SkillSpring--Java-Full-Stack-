package com.library.service;

import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class containing business logic for library book operations.
 *
 * EX6: @Service marks this class for component scanning.
 * EX7: Supports both constructor injection and setter injection.
 */
@Service  // EX6 — component scanning picks this up as a Spring-managed bean
public class BookService {

    private BookRepository bookRepository;

    // EX7: Constructor injection
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookService instantiated via constructor injection.");
    }

    // No-arg constructor also kept so XML <bean> without constructor-arg still works
    public BookService() {
        System.out.println("BookService instantiated (no-arg constructor).");
    }

    /**
     * EX7: Setter injection — Spring also calls this when
     * <property name="bookRepository" ref="bookRepository" /> is present in XML.
     */
    @Autowired(required = false)  // required=false so constructor-only wiring also works
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookRepository injected into BookService via setter.");
    }

    // Getter provided for completeness / testing
    public BookRepository getBookRepository() {
        return bookRepository;
    }

    /**
     * Retrieves and prints all books from the repository.
     */
    public void listAllBooks() {
        List<String> books = bookRepository.findAllBooks();
        System.out.println("\n--- Library Catalogue ---");
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            books.forEach(title -> System.out.println("  - " + title));
        }
        System.out.println("-------------------------\n");
    }

    /**
     * Adds a new book to the library.
     */
    public void addBook(String title) {
        bookRepository.save(title);
    }
}
