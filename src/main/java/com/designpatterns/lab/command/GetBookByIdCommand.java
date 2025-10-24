package com.designpatterns.lab.command;

import com.designpatterns.lab.model.Book;

import java.util.Optional;

public class GetBookByIdCommand implements Command<Optional<Book>> {
    private final CommandContext context;
    private final Long bookId;

    public GetBookByIdCommand(CommandContext context, Long bookId) {
        this.context = context;
        this.bookId = bookId;
    }

    @Override
    public Optional<Book> execute() {
        return context.getBooksService().getBookById(bookId);
    }
}