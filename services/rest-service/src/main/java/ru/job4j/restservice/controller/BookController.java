package ru.job4j.restservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value="v1/book")
public class BookController {

    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllBooks() {
        List<String> result = new ArrayList<>();
        result.add("{\"id\" : 1,\"name\" : \"nameOfBook1\"}");
        result.add("{\"id\" : 2,\"name\" : \"nameOfBook1\"}");
        result.add("{\"id\" : 3,\"name\" : \"nameOfBook1\"}");
        return ResponseEntity.ok(result);

    }

    @GetMapping("/{bookId}")
    public String getOneBook(@PathVariable String bookId) {
        System.out.println(bookId);
        String result = "{\"id\" : 1,\"name\" : \"nameOfBook1\"}";
        return result;
    }

}
