package com.matheusmaciel.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.matheusmaciel.domain.BookStoreEvent;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class bookStoreEventController {

    @PostMapping("/v1/bookstore-events")
    public ResponseEntity<BookStoreEvent> createBookStoreEvent(@RequestBody @Valid BookStoreEvent bookStoreEvent)
            throws JsonProcessingException {
                return null;
            };
}
