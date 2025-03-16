package com.matheusmaciel.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AutoCreateConfig {

    // cria o  config e adiciona o admin properties no application.yml
    // não recomendado para produção

    @Bean
    public NewTopic bookStoreEvents(){

        return TopicBuilder.name("bookstore-events")
                .partitions(3)
                .replicas(3)
                .build();
    }
}
