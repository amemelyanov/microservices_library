package ru.job4j.restservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ws.soap.client.SoapFaultClientException;
import ru.job4j.restservice.service.BookService;
import ru.job4j.restservice.wsdl.BookInfo;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value="v1/soap/book")
public class BookSoapController {

    private final BookService bookService;

    public BookSoapController(@Qualifier("bookServiceSoapImpl") BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookInfo>> findAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookInfo> findById(@PathVariable Long bookId) throws Exception {
        return ResponseEntity.ok(bookService.findById(bookId));
    }

    @ExceptionHandler(value = {SoapFaultClientException.class})
    @ResponseBody
    public ResponseEntity<String> soapFaultClientException(Exception e) {
        log.error(e.getLocalizedMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getLocalizedMessage());
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
