package ru.job4j.restservice.service;

import ru.job4j.restservice.wsdl.BookDto;

import java.util.List;

public interface BookService {

    BookDto findById(long id);

    List<BookDto> findAll();
}
