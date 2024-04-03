package ru.job4j.libraryservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.job4j.libraryservice.service.book.BookService;
import ru.job4j.libraryservice.ws.BookDto;
import ru.job4j.libraryservice.ws.FindAllBooksResponse;
import ru.job4j.libraryservice.ws.FindBookByIdRequest;
import ru.job4j.libraryservice.ws.FindBookByIdResponse;

import java.util.List;

/**
 * Контроллер для работы с книгами через SOAP
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.libraryservice.model.Book
 */
@Endpoint
@AllArgsConstructor
public class BookSoapController {

    /**
     * URL для пространства имен SOAP
     */
    private static final String NAMESPACE_URI = "http://libraryservice.job4j.ru/ws";

    /**
     * Объект для доступа к методам BookService
     */
    private final BookService bookService;

    /**
     * Метод получает объект запроса от клиента через SOAP для поиска книги по идентификатору книги,
     * вызвает метод сервисного слоя {@link BookService#findById(long)}, передавая в качестве параметра
     * идентификатор книги, получает обратно объект dto книги и возвращает объект ответа клиенту через SOAP.
     *
     * @param request запрос клиента
     * @return возвращает объект ответа содержащего книгу
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findBookByIdRequest")
    @ResponsePayload
    public FindBookByIdResponse findBookById(@RequestPayload FindBookByIdRequest request) {
        FindBookByIdResponse response = new FindBookByIdResponse();
        response.setBookDto(bookService.findById(request.getBookId()));
        return response;
    }

    /**
     * Метод получает объект запроса от клиента для выгрузки всех книг и их возврата,
     * вызвает метод сервисного слоя {@link BookService#findAll()}, и возвращает список
     * объектов dto книг клиенту через SOAP.
     *
     * @return объект ответа содержащего список объектов dto всех книг
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findAllBooksRequest")
    @ResponsePayload
    public FindAllBooksResponse findAllBooks() {
        FindAllBooksResponse response = new FindAllBooksResponse();
        List<BookDto> booksInfoList = bookService.findAll();
        response.getBookDto().addAll(booksInfoList);
        return response;
    }
}