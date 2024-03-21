package ru.job4j.libraryservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.libraryservice.mapper.BookMapper;
import ru.job4j.libraryservice.model.Book;
import ru.job4j.libraryservice.repository.BookRepository;
import ru.job4j.libraryservice.ws.BookDto;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookDto findById(long id) {
        return bookMapper.getBookDtoFromBook(bookRepository.findById(id)
                .orElse(new Book()));
    }

    public List<BookDto> findAll() {
        List<BookDto> bookDtoList = new ArrayList<>();
        bookRepository.findAll().forEach(
                book -> bookDtoList.add(bookMapper.getBookDtoFromBook(book)));
        return bookDtoList;
    }
}
