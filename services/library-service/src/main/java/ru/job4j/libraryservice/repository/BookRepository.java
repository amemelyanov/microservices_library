package ru.job4j.libraryservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.libraryservice.model.Book;

import java.util.List;

/**
 * Хранилище книг
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.libraryservice.model.Book
 */
public interface BookRepository extends CrudRepository<Book, Long> {

    /**
     * Метод выполняет возврат списка всех книг.
     *
     * @return список всех книг
     */
    List<Book> findAll();
}
