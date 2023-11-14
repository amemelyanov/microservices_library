package ru.job4j.libraryservice.service;

import org.springframework.stereotype.Service;
import ru.job4j.libraryservice.model.Book;
import ru.job4j.libraryservice.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book findById(Integer id) {
        Optional<Book> product = bookRepository.findById(id);
        if (product.isEmpty()) {
            throw new IllegalStateException("Book could not found for given id:" + id);
        }
        return product.get();
    }

    public List<Book> findAll() {
        List<Book> result = new ArrayList<>();
        bookRepository.findAll().forEach(result::add);
        return result;
    }

}
