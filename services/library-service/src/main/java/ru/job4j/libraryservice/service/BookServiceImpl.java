package ru.job4j.libraryservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.libraryservice.mapper.BookMapper;
import ru.job4j.libraryservice.repository.BookRepository;
import ru.job4j.libraryservice.ws.BookInfo;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookInfo findById(Long id) {
        return bookMapper.getBookInfoFromBook(bookRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Book could not found for given id:" + id)));
    }

    public List<BookInfo> findAll() {
        List<BookInfo> bookInfoList = new ArrayList<>();
        bookRepository.findAll().forEach(
                book -> bookInfoList.add(bookMapper.getBookInfoFromBook(book)));
        return bookInfoList;
     }
}
