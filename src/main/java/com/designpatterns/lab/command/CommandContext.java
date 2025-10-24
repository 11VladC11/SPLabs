package com.designpatterns.lab.command;

import com.designpatterns.lab.service.BooksService;

public class CommandContext {
    private final BooksService booksService;

    public CommandContext(BooksService booksService) {
        this.booksService = booksService;
    }

    public BooksService getBooksService() {
        return booksService;
    }
}