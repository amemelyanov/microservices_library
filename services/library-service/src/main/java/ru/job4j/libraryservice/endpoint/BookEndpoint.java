package ru.job4j.libraryservice.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.job4j.libraryservice.model.GetBookRequest;
import ru.job4j.libraryservice.model.GetBookResponse;
import ru.job4j.libraryservice.service.BookService;


@Endpoint
public class BookEndpoint {
    private static final String NAMESPACE_URI = "http://libraryservice.job4j.ru/model";

    private final BookService bookService;

    @Autowired
    public BookEndpoint(BookService bookService) {
        this.bookService = bookService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBookRequest")
    @ResponsePayload
    public GetBookResponse getCountry(@RequestPayload GetBookRequest request) {
        GetBookResponse response = new GetBookResponse();
        response.setBook(bookService.findById(request.getId()));

        return response;
    }
}