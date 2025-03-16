package com.matheusmaciel.domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookStoreEvent {

    private Integer bookStoreEventId;
    private BookStoreEventType bookStoreEventType;
    private Book book;
}
