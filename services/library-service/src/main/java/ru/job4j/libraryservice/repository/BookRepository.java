package ru.job4j.libraryservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.libraryservice.model.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findAll();
}
