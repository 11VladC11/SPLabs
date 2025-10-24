package com.designpatterns.lab.command;

import com.designpatterns.lab.model.Book;

public class CreateBookCommand implements Command<Book> {
    private final CommandContext context;
    private final Book book;

    public CreateBookCommand(CommandContext context, Book book) {
        this.context = context;
        this.book = book;
    }

    @Override
    public Book execute() {
        // Simulate time-consuming ISBN retrieval
        try {
            Thread.sleep(1000); // Simulate external service call
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return context.getBooksService().createBook(book);
    }
}