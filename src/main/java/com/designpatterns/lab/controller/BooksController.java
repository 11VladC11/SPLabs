package com.designpatterns.lab.controller;

import com.designpatterns.lab.command.*;
import com.designpatterns.lab.model.Book;
import com.designpatterns.lab.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final CommandExecutor commandExecutor;

    @Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
        this.commandExecutor = new CommandExecutor();
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        CommandContext context = new CommandContext(booksService);
        GetAllBooksCommand command = new GetAllBooksCommand(context);
        List<Book> books = commandExecutor.executeSync(command);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        CommandContext context = new CommandContext(booksService);
        GetBookByIdCommand command = new GetBookByIdCommand(context, id);
        Optional<Book> book = commandExecutor.executeSync(command);
        return book.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createBook(@RequestBody Book book) {
        CommandContext context = new CommandContext(booksService);
        CreateBookCommand command = new CreateBookCommand(context, book);
        
        // Asynchronous processing for POST (due to external ISBN service)
        String requestId = commandExecutor.executeAsync(command);
        
        Map<String, String> response = new HashMap<>();
        response.put("requestId", requestId);
        response.put("message", "Book creation request accepted");
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        CommandContext context = new CommandContext(booksService);
        UpdateBookCommand command = new UpdateBookCommand(context, id, book);
        Optional<Book> updatedBook = commandExecutor.executeSync(command);
        return updatedBook.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        CommandContext context = new CommandContext(booksService);
        DeleteBookCommand command = new DeleteBookCommand(context, id);
        boolean deleted = commandExecutor.executeSync(command);
        return deleted ? ResponseEntity.noContent().build() 
                       : ResponseEntity.notFound().build();
    }

    @GetMapping("/status/{requestId}")
    public ResponseEntity<CommandExecutor.CommandStatus> getRequestStatus(@PathVariable String requestId) {
        CommandExecutor.CommandStatus status = commandExecutor.getStatus(requestId);
        if ("NOT_FOUND".equals(status.getStatus())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(status);
    }
}