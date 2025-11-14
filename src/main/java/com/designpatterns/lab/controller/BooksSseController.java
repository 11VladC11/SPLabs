package com.designpatterns.lab.controller;

import com.designpatterns.lab.observer.AllBooksSubject;
import com.designpatterns.lab.observer.SseObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class BooksSseController {
    @Autowired
    private AllBooksSubject allBooksSubject;

    @GetMapping(value = "/books-sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamBooks() {
        SseEmitter emitter = new SseEmitter(0L);
        SseObserver observer = new SseObserver(emitter);
        allBooksSubject.attach(observer);

        emitter.onCompletion(() -> allBooksSubject.detach(observer));
        emitter.onTimeout(() -> allBooksSubject.detach(observer));
        emitter.onError((ex) -> allBooksSubject.detach(observer));

        return emitter;
    }
}
