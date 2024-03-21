package ru.job4j.libraryservice.controller;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import ru.job4j.libraryservice.dto.ListBookDto;
import ru.job4j.libraryservice.service.BookService;
import ru.job4j.libraryservice.ws.BookDto;

@Component
@AllArgsConstructor
public class KafkaController {

    private final BookService bookService;

    @KafkaListener(groupId = "${library-project.consumer-group}", topics = "${library-project.send-topics-by-id}",
            containerFactory = "kafkaListenerContainerFactory")
    @SendTo
    public BookDto listenById(ConsumerRecord<String, BookDto> consumerRecord) {
        return bookService.findById(consumerRecord.value().getId());
    }

    @KafkaListener(groupId = "${library-project.consumer-group}", topics = "${library-project.send-topics-all}",
            containerFactory = "listKafkaListenerContainerFactory")
    @SendTo
    public ListBookDto listenAll(ConsumerRecord<String, BookDto> consumerRecord) {
        return new ListBookDto(bookService.findAll());
    }
}
