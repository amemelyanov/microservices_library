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

/**
 * Реализация сервиса по работе с книгами с использованием SOAP
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.restservice.service.BookService
 */
@Slf4j
@AllArgsConstructor
public class BookServiceSoap extends WebServiceGatewaySupport implements BookService {

    /**
     * SoapAction для запроса поиска книги по идентификатору.
     */
    public static final String FIND_BOOK_BY_ID_REQUEST = "http://library-service:8081/ws/findBookByIdRequest";

    /**
     * SoapAction для запроса поиска всех книг.
     */
    public static final String FIND_ALL_BOOK_REQUEST = "http://library-service:8081/ws/findAllBooksRequest";

    /**
     * Объект для доступа к методам BookMapper
     */
    private final BookMapper bookMapper;

    /**
     * Метод выполняет возврат книги по идентификатору книги. Объект dto книги, созданный
     * на основе идентификатора книги, передается в вызываемый метод
     * {@link org.springframework.ws.client.core.WebServiceTemplate#marshalSendAndReceive(Object)},
     * который возвращает объект response полученный от сервера. Из объекта response извлекается объект dto книги,
     * который преобразовывается с помощью метода {@link BookMapper#getBookFromBookDto(BookDto)} в книгу
     * и возвращается из метода. Если книга не найдена на стороне сервера,
     * выбрасывается исключение ResourceNotFoundException.
     *
     * @param bookId идентификатор книги
     * @return картинка обложки книги в виде байтового массива
     */
    @Override
    public Book findById(long bookId) {
        log.info("Вызов метода findById() класса BookServiceSoap с параметром bookId = {}", bookId);
        FindBookByIdRequest request = new FindBookByIdRequest();
        request.setBookId(bookId);
        FindBookByIdResponse response = (FindBookByIdResponse) getWebServiceTemplate().marshalSendAndReceive(request,
                new SoapActionCallback(FIND_BOOK_BY_ID_REQUEST));
        log.info("Получен ответ от SOAP сервиса в методе findById() класса BookServiceSoap с объектом {}", response);
        BookDto bookDto = response.getBookDto();
        if (bookDto.getId() == 0) {
            throw new ResourceNotFoundException("Книга не найдена для данного id: " + bookId);
        }
        return bookMapper.getBookFromBookDto(response.getBookDto());
    }

    /**
     * Метод выполняет возврат списка всех книг. Для полученния списка книг вызывается метод
     * {@link org.springframework.ws.client.core.WebServiceTemplate#marshalSendAndReceive(Object)},
     * который возвращает объект response полученный от сервера. Из объекта response извлекается список объектов dto
     * книг, который преобразовывается с помощью метода {@link BookMapper#getListBookFromListBookDto(List)} в
     * список книг и возвращается из метода.
     *
     * @return список всех книг
     */
    @Override
    public List<Book> findAll() {
        log.info("Вызов метода findAll() класса BookServiceSoap");
        FindAllBooksRequest request = new FindAllBooksRequest();
        FindAllBooksResponse response = (FindAllBooksResponse) getWebServiceTemplate().marshalSendAndReceive(request,
                new SoapActionCallback(FIND_ALL_BOOK_REQUEST));
        log.info("Получен ответ от SOAP сервиса в методе findAll() класса BookServiceSoap с объектом {}", response);
        return bookMapper.getListBookFromListBookDto(response.getBookDto());
    }

    /**
     * Метод выполняет возврат байтового массива картинки обложки книги по идентификатору книги. Объект dto книги,
     * созданный на основе идентификатора книги, передается в вызываемый метод
     * {@link org.springframework.ws.client.core.WebServiceTemplate#marshalSendAndReceive(Object)},
     * который возвращает объект response полученный от сервера. Из объекта response извлекается объект dto книги,
     * который преобразовывается с помощью метода {@link BookMapper#getCoverFromBookDto(BookDto)} в байтовый массив
     * и возвращается из метода. Если книга не найдена на стороне сервера, выбрасывается исключение
     * ResourceNotFoundException.
     *
     * @param bookId идентификатор книги
     * @return картинка обложки книги в виде байтового массива
     */
    @Override
    public byte[] findCoverById(long bookId) {
        log.info("Вызов метода findCoverById() класса BookServiceSoap с параметром bookId = {}", bookId);
        FindBookByIdRequest request = new FindBookByIdRequest();
        request.setBookId(bookId);
        FindBookByIdResponse response = (FindBookByIdResponse) getWebServiceTemplate().marshalSendAndReceive(request,
                new SoapActionCallback(FIND_BOOK_BY_ID_REQUEST));
        log.info("Получен ответ от SOAP сервиса в методе findById() класса BookServiceSoap с объектом {}", response);
        BookDto bookDto = response.getBookDto();
        if (bookDto.getId() == 0) {
            throw new ResourceNotFoundException("Не найдена обложка книги для данного id: " + bookId);
        }
        if (bookDto.getCover().length() == 0) {
            throw new ResourceNotFoundException("Не найдена обложка книги для данного id: " + bookId);
        }
        return bookMapper.getCoverFromBookDto(response.getBookDto());
    }
}
