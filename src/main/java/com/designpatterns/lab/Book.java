package com.designpatterns.lab;

import java.util.ArrayList;
import java.util.List;

public class Book extends Section {
    private List<Author> authors;

    public Book(String title) {
        super(title);
        this.authors = new ArrayList<>();
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
    }

    public List<Author> getAuthors() {
        return new ArrayList<>(authors);
    }

    public void addContent(Element element) {
        super.add(element);
    }

    @Override
    public void print() {
        System.out.println("Book: " + getTitle());
        if (!authors.isEmpty()) {
            System.out.println("Authors:");
            for (Author author : authors) {
                author.print();
            }
        }
        for (int i = 0; i < size(); i++) {
            get(i).print();
        }
    }
}