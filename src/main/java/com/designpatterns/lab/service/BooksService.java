package com.designpatterns.lab.service;

import com.designpatterns.lab.model.Book;
import com.designpatterns.lab.persistence.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BooksService {
    private final CrudRepository<Book, Long> booksRepository;

    @Autowired
    public BooksService(CrudRepository<Book, Long> booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> getAllBooks() {
        return booksRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return booksRepository.findById(id);
    }

    public Book createBook(Book book) {
        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
            book.setIsbn("ISBN-" + System.currentTimeMillis());
        }
        return booksRepository.save(book);
    }

    public Optional<Book> updateBook(Long id, Book updatedBook) {
        return booksRepository.findById(id)
                .map(existingBook -> {
                    existingBook.setTitle(updatedBook.getTitle());
                    existingBook.setAuthor(updatedBook.getAuthor());
                    existingBook.setIsbn(updatedBook.getIsbn());
                    return booksRepository.save(existingBook);
                });
    }

    public boolean deleteBook(Long id) {
        if (booksRepository.existsById(id)) {
            booksRepository.deleteById(id);
            return true;
        }
        return false;
    }
}