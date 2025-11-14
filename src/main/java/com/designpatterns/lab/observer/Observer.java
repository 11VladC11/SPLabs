package com.designpatterns.lab.observer;

import com.designpatterns.lab.model.Book;

public interface Observer {
    void update(Book book);
}