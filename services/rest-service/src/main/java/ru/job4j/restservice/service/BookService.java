package ru.job4j.restservice.service;

import ru.job4j.restservice.wsdl.BookInfo;

import java.util.List;

public interface BookService {

    BookInfo findById(Long id);

    List<BookInfo> findAll();
}
