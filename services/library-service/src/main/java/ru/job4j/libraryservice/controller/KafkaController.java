package ru.job4j.libraryservice.controller;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import ru.job4j.libraryservice.model.KafkaMessage;
import ru.job4j.libraryservice.service.BookService;
import ru.job4j.libraryservice.ws.BookInfo;

@Component
@AllArgsConstructor
public class KafkaController {

    private final BookService bookService;

    @KafkaListener(groupId = "${library-project.consumer-group}", topics = "${library-project.send-topics-by-id}",
            containerFactory = "kafkaListenerContainerFactory")
    @SendTo
    public BookInfo listenById(ConsumerRecord<String, BookInfo> consumerRecord) {
        return bookService.findById(consumerRecord.value().getId());
    }

    @KafkaListener(groupId = "${library-project.consumer-group}", topics = "${library-project.send-topics-all}",
            containerFactory = "listKafkaListenerContainerFactory")
    @SendTo
    public KafkaMessage listenAll(ConsumerRecord<String, BookInfo> consumerRecord) {
        return new KafkaMessage(bookService.findAll());
    }
}
