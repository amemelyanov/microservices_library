package ru.job4j.libraryservice.endpoint;

import lombok.AllArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.job4j.libraryservice.mapper.BookMapper;
import ru.job4j.libraryservice.service.BookService;
import ru.job4j.libraryservice.ws.BookInfo;
import ru.job4j.libraryservice.ws.FindAllBooksResponse;
import ru.job4j.libraryservice.ws.FindBookByIdRequest;
import ru.job4j.libraryservice.ws.FindBookByIdResponse;

import java.util.List;

@Endpoint
@AllArgsConstructor
public class BookEndpoint {
    private static final String NAMESPACE_URI = "http://libraryservice.job4j.ru/ws";

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findBookByIdRequest")
    @ResponsePayload
    public FindBookByIdResponse findBookById(@RequestPayload FindBookByIdRequest request) {
        FindBookByIdResponse response = new FindBookByIdResponse();
        response.setBookInfo(bookService.findById(request.getBookId()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findAllBooksRequest")
    @ResponsePayload
    public FindAllBooksResponse findAllBooks() {
        FindAllBooksResponse response = new FindAllBooksResponse();
        List<BookInfo> booksInfoList = bookService.findAll();
        response.getBookInfo().addAll(booksInfoList);
        return response;
    }
}