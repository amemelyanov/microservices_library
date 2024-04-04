package ru.job4j.libraryservice.service.book;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.libraryservice.mapper.BookMapper;
import ru.job4j.libraryservice.model.Book;
import ru.job4j.libraryservice.repository.BookRepository;
import ru.job4j.libraryservice.ws.BookDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация сервиса по работе с книгами
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see BookService
 */
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    /**
     * Объект для доступа к методам BookRepository
     */
    private final BookRepository bookRepository;

    /**
     * Объект для доступа к методам BookMapper
     */
    private final BookMapper bookMapper;

    /**
     * Метод получает идентификатор книги, вызывает метод слоя репозитория {@link BookRepository#findById(Object)},
     * передавая ему идентификатор книги. Полученную из репозитория книгу, преоразовывает в объект dto с помощью метода
     * {@link BookMapper#getBookDtoFromBook(Book)}, который возвращает из метода.
     *
     * @param bookId идентификатор
     * @return dto книга
     */
    public BookDto findById(long bookId) {
        return bookMapper.getBookDtoFromBook(bookRepository.findById(bookId)
                .orElse(new Book()));
    }

    /**
     * Метод вызывает метод слоя репозитория {@link BookRepository#findAll()} и использую метод
     * {@link BookMapper#getListBookDtoFromListBook(List)}, возвращает список объектов dto книг.
     *
     * @return список объектов dto книг
     */
    public List<BookDto> findAll() {
        return bookMapper.getListBookDtoFromListBook(bookRepository.findAll());
    }
}
