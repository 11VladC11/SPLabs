package com.designpatterns.lab.service;

import com.designpatterns.lab.model.Book;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BooksService {
    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public BooksService() {
        // Initialize with some sample data
        Book book1 = new Book(idCounter.getAndIncrement(), "Effective Java", "Joshua Bloch", "978-0134685991");
        Book book2 = new Book(idCounter.getAndIncrement(), "Clean Code", "Robert Martin", "978-0132350884");
        books.put(book1.getId(), book1);
        books.put(book2.getId(), book2);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public Optional<Book> getBookById(Long id) {
        return Optional.ofNullable(books.get(id));
    }

    public Book createBook(Book book) {
        if (book.getId() == null) {
            book.setId(idCounter.getAndIncrement());
        }
        // Simulate obtaining ISBN from external service (takes time)
        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
            book.setIsbn("ISBN-" + System.currentTimeMillis());
        }
        books.put(book.getId(), book);
        return book;
    }

    public Optional<Book> updateBook(Long id, Book updatedBook) {
        if (!books.containsKey(id)) {
            return Optional.empty();
        }
        updatedBook.setId(id);
        books.put(id, updatedBook);
        return Optional.of(updatedBook);
    }

    public boolean deleteBook(Long id) {
        return books.remove(id) != null;
    }
}