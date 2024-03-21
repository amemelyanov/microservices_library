package ru.job4j.restservice.service;

import ru.job4j.restservice.wsdl.BookDto;

import java.util.List;

public interface BookService {

    BookDto findById(Long id);

    List<BookDto> findAll();
}
