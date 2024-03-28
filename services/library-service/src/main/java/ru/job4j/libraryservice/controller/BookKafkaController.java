package ru.job4j.libraryservice.controller;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import ru.job4j.libraryservice.dto.ListBookDto;
import ru.job4j.libraryservice.service.BookService;
import ru.job4j.libraryservice.ws.BookDto;

/**
 * Контроллер для работы с книгами через Kafka
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.libraryservice.model.Book
 */
@Component
@AllArgsConstructor
public class BookKafkaController {

    /**
     * Объект для доступа к методам BookService
     */
    private final BookService bookService;

    /**
     * Метод получает объект запроса от клиента через Kafka для поиска книги по идентификатору,
     * вызвает метод сервисного слоя {@link BookService#findById(long)}, передавая в качестве параметра
     * идентификатор книги, получает ответ и возвращает книгу клиенту через Kafka.
     *
     * @param consumerRecord запрос клиента
     * @return возвращает ответ содержащий объект BookDto
     */
    @KafkaListener(groupId = "${library-project.consumer-group}", topics = "${library-project.send-topics-by-id}",
            containerFactory = "kafkaListenerContainerFactory")
    @SendTo
    public BookDto listenById(ConsumerRecord<String, BookDto> consumerRecord) {
        return bookService.findById(consumerRecord.value().getId());
    }

    /**
     * Метод получает объект запроса от клиента через Kafka для поиска всех книг,
     * вызвает метод сервисного слоя {@link BookService#findAll()}, и возвращает список
     * объектов dto книг клиенту через Kafka.
     *
     * @param consumerRecord запрос клиента
     * @return возвращает ответ содержащий объект ListBookDto, содержащий список всех книг
     */
    @KafkaListener(groupId = "${library-project.consumer-group}", topics = "${library-project.send-topics-all}",
            containerFactory = "listKafkaListenerContainerFactory")
    @SendTo
    public ListBookDto listenAll(ConsumerRecord<String, BookDto> consumerRecord) {
        return new ListBookDto(bookService.findAll());
    }
}
