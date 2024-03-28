package ru.job4j.restservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ws.soap.client.SoapFaultClientException;
import ru.job4j.restservice.model.Book;
import ru.job4j.restservice.service.BookService;

import java.util.List;

/**
 * Контроллер для работы с книгами через SOAP
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.restservice.model.Book
 */
@Tag(name = "Book SOAP Controller", description = "Book SOAP API")
@Slf4j
@RestController
@RequestMapping(value = "v1/soap/book")
public class BookSoapController {

    /**
     * Объект для доступа к методам BookService
     */
    private final BookService bookService;

    /**
     * Конструктор
     *
     * @param bookService объект для доступа к методам BookService
     */
    public BookSoapController(@Qualifier("bookServiceSoap") BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Метод возвращает список всех книг, для их получения вызывает метод сервисного слоя {@link BookService#findAll()}
     *
     * @return список книг
     */
    @Operation(summary = "Получение списка всех книг посредством внутреннего взаимодействия на основе SOAP")
    @GetMapping("/all")
    public ResponseEntity<List<Book>> findAll() {
        log.info("Вызов метода findAll() класса BookSoapController");
        return ResponseEntity.ok(bookService.findAll());
    }

    /**
     * Метод возвращает книгу по переданному клиентом идентификатору, идентификатор передается вызываемому методу
     * сервисного слоя {@link BookService#findById(long)}, который возвращает найденную книгу.
     *
     * @param bookId идентификатор книги
     * @return книга
     */
    @Operation(summary = "Получение книги по id посредством внутреннего взаимодействия на основе SOAP")
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> findById(@PathVariable long bookId) {
        log.info("Вызов метода findById() класса BookSoapController с параметром bookId = {}", bookId);
        return ResponseEntity.ok(bookService.findById(bookId));
    }

    /**
     * Метод возвращает обложку книги в виде байтового массива по переданному клиентом идентификатору,
     * идентификатор передается вызываемому методу сервисного слоя {@link BookService#findById(long)},
     * который возвращает найденную книгу.
     *
     * @param bookId идентификатор книги
     * @return обложка книги в виде байтового массива
     */
    @Operation(summary = "Получение обложки книги по id посредством внутреннего взаимодействия на основе SOAP")
    @GetMapping(value = "/{bookId}/cover", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> findCoverById(@PathVariable long bookId) {
        log.info("Вызов метода findCoverById() класса BookSoapController с параметром bookId = {}", bookId);
        return ResponseEntity.ok(bookService.findCoverById(bookId));
    }

    /**
     * Выполняет локальный (уровня контроллера) перехват исключений
     * SoapFaultClientException, в случае перехвата, выполняет логирование
     * и возвращает клиенту ответ с комментарием исключения.
     *
     * @param e перехваченное исключение
     * @return объект ResponseEntity с описанием исключения
     */
    @ExceptionHandler(value = {SoapFaultClientException.class})
    @ResponseBody
    public ResponseEntity<String> soapFaultClientException(Exception e) {
        log.error("Вызов метода soapFaultClientException() класса BookSoapController при обработке исключения"
                + " SoapFaultClientException", e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getLocalizedMessage());
    }

    /**
     * Выполняет локальный (уровня контроллера) перехват исключений
     * Exception, в случае перехвата, выполняет логирование
     * и возвращает клиенту ответ с комментарием исключения.
     *
     * @param e перехваченное исключение
     * @return объект ResponseEntity с описанием исключения
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResponseEntity<String> connectException(Exception e) {
        log.error("Вызов метода connectException() класса BookSoapController при обработке исключения Exception", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getLocalizedMessage());
    }

}
