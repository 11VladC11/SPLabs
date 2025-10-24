package com.designpatterns.lab.command;

public class DeleteBookCommand implements Command<Boolean> {
    private final CommandContext context;
    private final Long bookId;

    public DeleteBookCommand(CommandContext context, Long bookId) {
        this.context = context;
        this.bookId = bookId;
    }

    @Override
    public Boolean execute() {
        return context.getBooksService().deleteBook(bookId);
    }
}