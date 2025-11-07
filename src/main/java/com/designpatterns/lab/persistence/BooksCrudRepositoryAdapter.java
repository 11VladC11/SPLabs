package com.designpatterns.lab.persistence;

import com.designpatterns.lab.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BooksCrudRepositoryAdapter implements CrudRepository<Book, Long> {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksCrudRepositoryAdapter(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return booksRepository.findById(id);
    }

    @Override
    public Book save(Book entity) {
        return booksRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        booksRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return booksRepository.existsById(id);
    }
}