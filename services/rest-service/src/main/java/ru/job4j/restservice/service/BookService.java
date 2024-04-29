package ru.job4j.restservice.service;

import ru.job4j.restservice.model.Book;

import java.util.List;

/**
 * Сервис по работе с книгами
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.restservice.model.Book
 */
public interface BookService {

    /**
     * Метод выполняет возврат книги по идентификатору книги.
     *
     * @param bookId идентификатор книги
     * @return книгу
     */
    Book findById(long bookId);

    /**
     * Метод выполняет возврат списка всех книг.
     *
     * @return список всех книг
     */
    List<Book> findAll();

    /**
     * Метод выполняет возврат картинки обложки книги по идентификатору книги.
     *
     * @param bookId идентификатор книги
     * @return картинка обложки книги в виде байтового массива
     */
    byte[] findCoverById(long bookId);
}
