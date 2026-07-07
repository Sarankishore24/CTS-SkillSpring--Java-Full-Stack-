package com.library.main;

import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Entry point for the Library Management application.
 * Loads the Spring ApplicationContext from applicationContext.xml
 * and exercises the BookService bean.
 */
public class LibraryManagementApplication {

    public static void main(String[] args) {

        System.out.println("Loading Spring ApplicationContext...\n");

        // Load context from the XML configuration on the classpath
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the BookService bean — Spring has already wired BookRepository into it
        BookService bookService = (BookService) context.getBean("bookService");

        // Test: list books that were pre-loaded by the repository
        bookService.listAllBooks();

        // Test: add a new book and list again
        bookService.addBook("Clean Code by Robert C. Martin");
        bookService.listAllBooks();

        // Close the context to release resources
        ((ClassPathXmlApplicationContext) context).close();
        System.out.println("Spring ApplicationContext closed.");
    }
}
