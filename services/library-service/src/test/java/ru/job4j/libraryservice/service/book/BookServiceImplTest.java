package ru.job4j.libraryservice.service.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.job4j.libraryservice.mapper.BookMapper;
import ru.job4j.libraryservice.model.Book;
import ru.job4j.libraryservice.repository.BookRepository;
import ru.job4j.libraryservice.ws.BookDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тест класс реализации сервисов
 * @see ru.job4j.libraryservice.service.cover.CoverServiceMinio
 * @author Alexander Emelyanov
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    /**
     Константа id
     */
    public static final long BOOK_ID = 1;

    /**
     Моск объекта CoverMinioRepository
     */
    @Mock
    private BookRepository bookRepository;

    /**
     Моск объекта CoverMinioRepository
     */
    @Mock
    private BookMapper bookMapper;

    /**
     Объект для внедрения mock BookServiceImpl
     */
    @InjectMocks
    private BookServiceImpl bookService;

    private BookDto bookDto;
    private Book book;

    /**
     * Метод создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    void init() {
        bookDto = new BookDto();
        bookDto.setId(BOOK_ID);

        book = Book.builder().id(BOOK_ID).build();
    }

    /**
     * Выполняется проверка работы метода по получению объекта bookDto из хранилища,
     * если файл в хранилище присутствует.
     */
    @Test
    void findByIdWhenIdExists() {
        when(bookRepository.findById(BOOK_ID))
                .thenReturn(Optional.of(book));
        when(bookMapper.getBookDtoFromBook(book))
                .thenReturn(bookDto);

        BookDto actualResult = bookService.findById(BOOK_ID);
        BookDto expectedResult = bookDto;

        verify(bookRepository).findById(BOOK_ID);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    /**
     * Выполняется проверка работы метода по получению объекта bookDto из хранилища,
     * если файл в хранилище отсутствует.
     */
    @Test
    void findByIdWhenIdNotExist() {
        Book bookWithNoId = new Book();
        BookDto bookDtoWitNoId = new BookDto();
        when(bookRepository.findById(BOOK_ID))
                .thenReturn(Optional.empty());
        when(bookMapper.getBookDtoFromBook(bookWithNoId))
                .thenReturn(bookDtoWitNoId);

        BookDto actualResult = bookService.findById(BOOK_ID);
        BookDto expectedResult = bookDtoWitNoId;

        verify(bookRepository).findById(BOOK_ID);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    /**
     * Выполняется проверка работы метода по получению списка объектов bookDto из хранилища,
     * если хранилище не пустое.
     */
    @Test
    void findAllIfBooksExist() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());
        List<BookDto> bookDtos = new ArrayList<>();
        bookDtos.add(new BookDto());
        bookDtos.add(new BookDto());

        when(bookRepository.findAll())
                .thenReturn(books);
        when(bookMapper.getListBookDtoFromListBook(books))
                .thenReturn(bookDtos);

        List<BookDto> actualResult = bookService.findAll();
        List<BookDto> expectedResult = bookDtos;

        verify(bookRepository).findAll();
        assertAll(
                () -> assertThat(actualResult).hasSameElementsAs(expectedResult),
                () -> assertThat(actualResult).hasSize(2)
        );
    }

    /**
     * Выполняется проверка работы метода по получению списка объектов bookDto из хранилища,
     * если хранилище пустое.
     */
    @Test
    void findAllIfBooksNotExist() {
        List<Book> books = new ArrayList<>();
        List<BookDto> bookDtos = new ArrayList<>();
        when(bookRepository.findAll())
                .thenReturn(books);
        when(bookMapper.getListBookDtoFromListBook(books))
                .thenReturn(bookDtos);

        List<BookDto> actualResult = bookService.findAll();
        List<BookDto> expectedResult = bookDtos;

        verify(bookRepository).findAll();
        assertThat(actualResult).isEmpty();
    }
}