package com.designpatterns.lab.command;

import com.designpatterns.lab.model.Book;

import java.util.Optional;

public class UpdateBookCommand implements Command<Optional<Book>> {
    private final CommandContext context;
    private final Long bookId;
    private final Book book;

    public UpdateBookCommand(CommandContext context, Long bookId, Book book) {
        this.context = context;
        this.bookId = bookId;
        this.book = book;
    }

    @Override
    public Optional<Book> execute() {
        return context.getBooksService().updateBook(bookId, book);
    }
}