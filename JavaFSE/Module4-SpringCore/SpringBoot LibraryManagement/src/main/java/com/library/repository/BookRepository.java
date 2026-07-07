package com.library.repository;

import com.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for Book entities.
 *
 * JpaRepository provides CRUD operations out of the box:
 *   save(), findById(), findAll(), deleteById(), etc.
 *
 * Custom query methods are derived automatically from method names.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Spring Data derives: SELECT * FROM books WHERE author = ?
    List<Book> findByAuthor(String author);

    // Spring Data derives: SELECT * FROM books WHERE title LIKE %?%
    List<Book> findByTitleContainingIgnoreCase(String titleFragment);
}
