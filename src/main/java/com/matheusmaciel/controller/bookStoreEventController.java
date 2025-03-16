package com.matheusmaciel.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.matheusmaciel.domain.BookStoreEvent;
import com.matheusmaciel.domain.BookStoreEventType;
import com.matheusmaciel.producer.BookStoreEventProducer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
public class bookStoreEventController {

    @Autowired
    BookStoreEventProducer bookStoreEventProducer;

    @PostMapping("/v1/bookstore-event")
    public ResponseEntity<BookStoreEvent> createBookStoreEvent(@RequestBody @Valid BookStoreEvent bookStoreEvent)
            throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {

        // um novo livro é adicionado a biblioteca online
        bookStoreEvent.setBookStoreEventType(BookStoreEventType.NEW);

        bookStoreEventProducer.sendBookStoreEventSynchronous(bookStoreEvent);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookStoreEvent);

    };

    @PutMapping("/v1/bookstore-event")
    public ResponseEntity<?> putLibraryEvent(@RequestBody @Valid BookStoreEvent bookStoreEvent)
            throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {

        if(bookStoreEvent.getBookStoreEventId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BookStoreEventId is required");
        }

        bookStoreEvent.setBookStoreEventType(BookStoreEventType.UPDATED);

        bookStoreEventProducer.sendBookStoreEventSynchronous(bookStoreEvent);

        return ResponseEntity.status(HttpStatus.OK).body(bookStoreEvent);
    }
}
