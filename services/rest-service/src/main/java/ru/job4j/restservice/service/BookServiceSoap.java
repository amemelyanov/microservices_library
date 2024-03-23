package ru.job4j.restservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import ru.job4j.restservice.exception.ResourceNotFoundException;
import ru.job4j.restservice.mapper.BookMapper;
import ru.job4j.restservice.model.Book;
import ru.job4j.restservice.wsdl.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class BookServiceSoap extends WebServiceGatewaySupport implements BookService {

    private final BookMapper bookMapper;

    @Override
    public Book findById(long bookId) {
        log.info("Вызов метода findById() класса BookServiceSoap с параметром bookId = {}", bookId);
        FindBookByIdRequest request = new FindBookByIdRequest();
        request.setBookId(bookId);
        FindBookByIdResponse response = (FindBookByIdResponse) getWebServiceTemplate().marshalSendAndReceive(request,
                new SoapActionCallback("http://library-service:8081/ws/findBookByIdRequest"));
        log.info("Получен ответ от SOAP сервиса в методе findById() класса BookServiceSoap с объектом {}", response);
        BookDto bookDto = response.getBookDto();
        if (bookDto.getId() == 0) {
            throw new ResourceNotFoundException("Книга не найдена для данного id: " + bookId);
        }
        return bookMapper.getBookFromBookDto(response.getBookDto());
    }

    @Override
    public List<Book> findAll() {
        log.info("Вызов метода findAll() класса BookServiceSoap");
        FindAllBooksRequest request = new FindAllBooksRequest();
        FindAllBooksResponse response = (FindAllBooksResponse) getWebServiceTemplate().marshalSendAndReceive(request,
                new SoapActionCallback("http://library-service:8081/ws/findAllBooksRequest"));
        log.info("Получен ответ от SOAP сервиса в методе findAll() класса BookServiceSoap с объектом {}", response);
        return bookMapper.getListBookFromListBookDto(response.getBookDto());
    }

    @Override
    public byte[] findCoverById(long bookId) {
        log.info("Вызов метода findCoverById() класса BookServiceSoap с параметром bookId = {}", bookId);
        FindBookByIdRequest request = new FindBookByIdRequest();
        request.setBookId(bookId);
        FindBookByIdResponse response = (FindBookByIdResponse) getWebServiceTemplate().marshalSendAndReceive(request,
                new SoapActionCallback("http://library-service:8081/ws/findBookByIdRequest"));
        log.info("Получен ответ от SOAP сервиса в методе findById() класса BookServiceSoap с объектом {}", response);
        BookDto bookDto = response.getBookDto();
        if (bookDto.getId() == 0) {
            throw new ResourceNotFoundException("Книга не найдена для данного id: " + bookId);
        }
        return bookMapper.getCoverFromBookDto(response.getBookDto());
    }
}
