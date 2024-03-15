package ru.job4j.restservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.restservice.mapper.BookMapper;
import ru.job4j.restservice.model.Book;
import ru.job4j.restservice.wsdl.BookInfo;
import ru.job4j.restservice.wsdl.FindAllBooksResponse;
import ru.job4j.restservice.wsdl.FindBookByIdResponse;

import java.util.List;

@AllArgsConstructor
@Service
public class BookServiceSoapImpl implements BookService {

    private BookClient bookClient;

    @Override
    public BookInfo findById(Long id) {
        FindBookByIdResponse response = bookClient.findBookById(id);
        return response.getBookInfo();
    }

    @Override
    public List<BookInfo> findAll() {
        FindAllBooksResponse allBooks = bookClient.findAllBooks();
        return allBooks.getBookInfo();
    }
}
