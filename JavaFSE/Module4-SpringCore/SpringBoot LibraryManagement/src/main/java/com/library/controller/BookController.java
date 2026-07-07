package com.library.controller;

import com.library.model.Book;
import com.library.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller exposing CRUD endpoints for the Book resource.
 *
 * Base URL: /api/books
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;

    // Spring injects BookRepository via constructor injection
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // ── CREATE ────────────────────────────────────────────────────
    // POST /api/books
    // Body: { "title": "...", "author": "...", "isbn": "..." }
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book saved = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ── READ ALL ──────────────────────────────────────────────────
    // GET /api/books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    // ── READ ONE ──────────────────────────────────────────────────
    // GET /api/books/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // ── UPDATE ────────────────────────────────────────────────────
    // PUT /api/books/{id}
    // Body: { "title": "...", "author": "...", "isbn": "..." }
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
                                           @RequestBody Book updatedBook) {
        return bookRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(updatedBook.getTitle());
                    existing.setAuthor(updatedBook.getAuthor());
                    existing.setIsbn(updatedBook.getIsbn());
                    return ResponseEntity.ok(bookRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ── DELETE ────────────────────────────────────────────────────
    // DELETE /api/books/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
