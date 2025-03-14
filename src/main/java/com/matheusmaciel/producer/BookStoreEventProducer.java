package com.matheusmaciel.producer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusmaciel.domain.BookStoreEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.support.SendResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@Component
@Slf4j
public class BookStoreEventProducer {
    @Autowired
    KafkaTemplate<Integer,String> kafkaTemplate;

    String topic = "bookstore-events";
    @Autowired
    ObjectMapper objectMapper;

    private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {

        // Manda um KafkaRecord com Headers usando o KafkaTemplate
        List<Header> recordHeaders = List.of(new RecordHeader("event-source", "scanner".getBytes()));

        return new ProducerRecord<>(topic, null, key, value, recordHeaders);
    }

    public SendResult<Integer, String>sendBookStoreEventSynchronous(BookStoreEvent bookStoreEvent)
            throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {

        Integer key = bookStoreEvent.getBookStoreEventId();
        String value = objectMapper.writeValueAsString(bookStoreEvent);
        SendResult<Integer,String> sendResult = null;
        try {
            sendResult = kafkaTemplate.sendDefault(key,value).get(1, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException e) {
            log.error("ExecutionException/InterruptedException sending a message with exception {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception sending a message with exception {}", e.getMessage());
            throw e;
        }

        return sendResult;

    }


}
