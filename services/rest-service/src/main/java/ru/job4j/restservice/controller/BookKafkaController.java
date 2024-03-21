package ru.job4j.restservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.restservice.mapper.BookMapper;
import ru.job4j.restservice.model.Book;
import ru.job4j.restservice.service.BookService;

import java.util.List;

@Tag(name = "Book Kafka Controller", description = "Book Kafka API")
@Slf4j
@RestController
@RequestMapping(value = "v1/kafka/book")
public class BookKafkaController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookKafkaController(@Qualifier("bookServiceKafkaImpl") BookService bookService,
                               BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @Operation(summary = "Получение списка всех книг посредством внутреннего взаимодействия на основе Kafka")
    @GetMapping("/all")
    public ResponseEntity<List<Book>> findAll() {
        log.info("Вызов метода findAll() класса BookKafkaController");
        return ResponseEntity.ok(bookMapper.getListBookFromListBookDto(bookService.findAll()));
    }

    @Operation(summary = "Получение книги по id посредством внутреннего взаимодействия на основе Kafka")
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> findById(@PathVariable Long bookId) {
        log.info("Вызов метода findById() класса BookKafkaController с параметром bookId = {}", bookId);
        return ResponseEntity.ok(bookMapper.getBookFromBookDto(bookService.findById(bookId)));
    }

    @Operation(summary = "Получение обложки книги по id посредством внутреннего взаимодействия на основе Kafka")
    @GetMapping(value = "/{bookId}/cover", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody ResponseEntity<byte[]> findCoverById(@PathVariable Long bookId) {
        log.info("Вызов метода findCoverById() класса BookKafkaController с параметром bookId = {}", bookId);
        return ResponseEntity.ok(bookMapper.getCoverFromBookDto(bookService.findById(bookId)));
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResponseEntity<String> connectException(Exception e) {
        log.error("Вызов метода connectException() класса BookKafkaController при обработке исключения Exception", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getLocalizedMessage());
    }

}
