package ru.job4j.restservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ws.soap.client.SoapFaultClientException;
import ru.job4j.restservice.model.Book;
import ru.job4j.restservice.service.BookService;

import java.util.List;

@Tag(name = "Book SOAP Controller", description = "Book SOAP API")
@Slf4j
@RestController
@RequestMapping(value = "v1/soap/book")
public class BookSoapController {

    private final BookService bookService;

    public BookSoapController(@Qualifier("bookServiceSoap") BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Получение списка всех книг посредством внутреннего взаимодействия на основе SOAP")
    @GetMapping("/all")
    public ResponseEntity<List<Book>> findAll() {
        log.info("Вызов метода findAll() класса BookSoapController");
        return ResponseEntity.ok(bookService.findAll());
    }

    @Operation(summary = "Получение книги по id посредством внутреннего взаимодействия на основе SOAP")
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> findById(@PathVariable long bookId) {
        log.info("Вызов метода findById() класса BookSoapController с параметром bookId = {}", bookId);
        return ResponseEntity.ok(bookService.findById(bookId));
    }

    @Operation(summary = "Получение обложки книги по id посредством внутреннего взаимодействия на основе SOAP")
    @GetMapping(value = "/{bookId}/cover", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> findCoverById(@PathVariable long bookId) {
        log.info("Вызов метода findCoverById() класса BookSoapController с параметром bookId = {}", bookId);
        return ResponseEntity.ok(bookService.findCoverById(bookId));
    }

    @ExceptionHandler(value = {SoapFaultClientException.class})
    @ResponseBody
    public ResponseEntity<String> soapFaultClientException(Exception e) {
        log.error("Вызов метода soapFaultClientException() класса BookSoapController при обработке исключения"
                + " SoapFaultClientException", e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getLocalizedMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResponseEntity<String> connectException(Exception e) {
        log.error("Вызов метода connectException() класса BookSoapController при обработке исключения Exception", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getLocalizedMessage());
    }

}
