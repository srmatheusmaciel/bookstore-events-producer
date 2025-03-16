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

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@Component
@Slf4j
public class BookStoreEventProducer {
    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    String topic = "bookstore-events";
    @Autowired
    private ObjectMapper objectMapper;

    public SendResult<Integer, String>sendBookStoreEventSynchronous(BookStoreEvent bookStoreEvent) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {

        Integer key = bookStoreEvent.getBookStoreEventId();
        String value = objectMapper.writeValueAsString(bookStoreEvent);
        SendResult<Integer,String> sendResult = null;
        try {
            sendResult = kafkaTemplate.sendDefault(key,value).get(1, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException e) {
            log.error("ExecutionException/InterruptedException Mandando uma mensagem com a exception {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Mandando uma mensagem com a exception {}", e.getMessage());
            throw e;
        }

        return sendResult;

    }



}

