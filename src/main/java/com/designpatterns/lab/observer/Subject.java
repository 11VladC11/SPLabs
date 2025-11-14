package com.designpatterns.lab.observer;

import com.designpatterns.lab.model.Book;

public interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers(Book book);
}