package ru.job4j.libraryservice.service;

import ru.job4j.libraryservice.ws.BookDto;

import java.util.List;

public interface BookService {

    BookDto findById(long id);

    List<BookDto> findAll();
}
