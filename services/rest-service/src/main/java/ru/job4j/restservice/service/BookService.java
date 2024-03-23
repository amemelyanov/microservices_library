package ru.job4j.restservice.service;

import ru.job4j.restservice.model.Book;

import java.util.List;

public interface BookService {

    Book findById(long bookId);

    List<Book> findAll();

    byte[] findCoverById(long bookId);
}
