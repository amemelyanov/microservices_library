package ru.job4j.libraryservice.service.book;

import ru.job4j.libraryservice.ws.BookDto;

import java.util.List;

/**
 * Сервис по работе с книгами
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.libraryservice.model.Book
 */
public interface BookService {

    /**
     * Метод выполняет возврат объекта dto книги по идентификатору книги.
     *
     * @param bookId идентификатор книги
     * @return список всех объектов dto книг
     */
    BookDto findById(long bookId);

    /**
     * Метод выполняет возврат списка всех объектов dto книг.
     *
     * @return список всех объектов dto книг
     */
    List<BookDto> findAll();
}
