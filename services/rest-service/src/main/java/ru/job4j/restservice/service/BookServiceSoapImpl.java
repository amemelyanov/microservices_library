package ru.job4j.restservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.restservice.wsdl.BookInfo;
import ru.job4j.restservice.wsdl.FindAllBooksResponse;
import ru.job4j.restservice.wsdl.FindBookByIdResponse;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class BookServiceSoapImpl implements BookService {

    private BookClient bookClient;

    @Override
    public BookInfo findById(Long bookId) {
        log.info("Вызов метода findById() класса BookServiceSoapImpl с параметром bookId = {}", bookId);
        FindBookByIdResponse response = bookClient.findBookById(bookId);
        return response.getBookInfo();
    }

    @Override
    public List<BookInfo> findAll() {
        log.info("Вызов метода findAll() класса BookServiceSoapImpl");
        FindAllBooksResponse allBooks = bookClient.findAllBooks();
        return allBooks.getBookInfo();
    }
}
