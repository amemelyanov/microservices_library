package ru.job4j.restservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.job4j.restservice.mapper.BookMapper;
import ru.job4j.restservice.model.Book;
import ru.job4j.restservice.service.BookService;
import ru.job4j.restservice.wsdl.BookInfo;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value="v1/kafka/book")
public class BookKafkaController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookKafkaController(@Qualifier("bookServiceKafkaImpl") BookService bookService
            , BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> findAll() {
        return ResponseEntity.ok(bookMapper.getListBookFromListBookInfo(bookService.findAll()));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> findById(@PathVariable Long bookId) throws Exception {
        return ResponseEntity.ok(bookMapper.getBookFromBookInfo(bookService.findById(bookId)));
    }

    @GetMapping(value = "/{bookId}/cover", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody ResponseEntity<byte[]> findCoverById(@PathVariable Long bookId) throws Exception {
        return ResponseEntity.ok(bookMapper.getCoverFromBookInfo(bookService.findById(bookId)));
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResponseEntity<String> connectException(Exception e) {
        log.error(e.getLocalizedMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getLocalizedMessage());
    }

}
