package ru.job4j.libraryservice.service;

import ru.job4j.libraryservice.ws.BookInfo;

import java.util.List;

public interface BookService {

    BookInfo findById(Long id);

    List<BookInfo> findAll();
}
