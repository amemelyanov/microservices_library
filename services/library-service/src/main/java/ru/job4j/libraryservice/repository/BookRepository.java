package ru.job4j.libraryservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.libraryservice.model.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {
}
