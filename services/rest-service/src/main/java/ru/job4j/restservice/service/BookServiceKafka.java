package ru.job4j.restservice.service;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.job4j.restservice.dto.ListBookDto;
import ru.job4j.restservice.exception.ResourceNotFoundException;
import ru.job4j.restservice.mapper.BookMapper;
import ru.job4j.restservice.model.Book;
import ru.job4j.restservice.wsdl.BookDto;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Реализация сервиса по работе с книгами с использованием Kafka
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.restservice.service.BookService
 */
@Service
@Slf4j
@NoArgsConstructor
public class BookServiceKafka implements BookService {

    /**
     * Объект для доступа к методам BookMapper
     */
    @Autowired
    private BookMapper bookMapper;

    /**
     * Объект для доступа к методам ReplyingKafkaTemplate<String, BookDto, BookDto>
     */
    @Autowired
    private ReplyingKafkaTemplate<String, BookDto, BookDto> templateById;

    /**
     * Объект для доступа к методам ReplyingKafkaTemplate<String, BookDto, ListBookDto>
     */
    @Autowired
    private ReplyingKafkaTemplate<String, BookDto, ListBookDto> templateAll;

    /**
     * Наименование топика для отправки объекта dto книги
     */
    @Value("${library-project.send-topics-by-id}")
    private String sendTopicsById;

    /**
     * Наименование топика для отправки списка объектов dto книг
     */
    @Value("${library-project.send-topics-all}")
    private String sendTopicsAll;

    /**
     * Метод выполняет возврат книги по идентификатору книги. Объект dto книги, созданный
     * на основе идентификатора книги, передается в вызываемый метод
     * {@link BookServiceKafka#kafkaRequestReplyById(BookDto)}, который возвращает объект
     * dto книги, полученный через Kafka. Объект dto книги, преобразованный с помощью метода
     * {@link BookMapper#getBookFromBookDto(BookDto)} возвращается из метода. Если книга не
     * найдена на стороне сервера, выбрасывается исключение ResourceNotFoundException.
     *
     * @param bookId идентификатор книги
     * @return картинка обложки книги в виде байтового массива
     */
    @SneakyThrows
    @Override
    public Book findById(long bookId) {
        log.info("Вызов метода findById() класса BookServiceKafka с параметром bookId = {}", bookId);
        BookDto bookDto = new BookDto();
        bookDto.setId(bookId);
        BookDto replyBookDto = kafkaRequestReplyById(bookDto);
        if (replyBookDto.getId() == 0) {
            throw new ResourceNotFoundException("Не найдена книга для данного id: " + bookId);
        }
        return bookMapper.getBookFromBookDto(kafkaRequestReplyById(bookDto));
    }

    /**
     * Метод выполняет возврат списка всех книг. Для полученния списка книг вызывается метод
     * {@link BookServiceKafka#kafkaRequestReplyAll(BookDto)}, которому передается новый пустой объект книги.
     * Возвращенный список объектов dto книг преобразуется в список книг с помощью метода
     * {@link BookMapper#getListBookFromListBookDto(List)} и возвращается из метода
     *
     * @return список всех книг
     */
    @SneakyThrows
    @Override
    public List<Book> findAll() {
        log.info("Вызов метода findAll() класса BookServiceKafka");
        BookDto bookDto = new BookDto();
        bookDto.setId(0);
        return bookMapper.getListBookFromListBookDto(kafkaRequestReplyAll(bookDto));
    }

    /**
     * Метод выполняет возврат картинки обложки книги по идентификатору книги. Объект dto
     * книги, созданный на основе идентификатора книги, передается в вызываемый метод
     * {@link BookServiceKafka#kafkaRequestReplyById(BookDto)}, который возвращает объект
     * dto книги, полученный через Kafka. Объект dto книги, преобразованный с помощью метода
     * {@link BookMapper#getCoverFromBookDto(BookDto)}, возвращается из метода. Если книга не
     * найдена на стороне сервера, выбрасывается исключение ResourceNotFoundException.
     *
     * @param bookId идентификатор книги
     * @return картинка обложки книги в виде байтового массива
     */
    @SneakyThrows
    @Override
    public byte[] findCoverById(long bookId) {
        log.info("Вызов метода findCoverById() класса BookServiceKafka с параметром bookId = {}", bookId);
        BookDto bookDto = new BookDto();
        bookDto.setId(bookId);
        BookDto replyBookDto = kafkaRequestReplyById(bookDto);
        if (replyBookDto.getId() == 0) {
            throw new ResourceNotFoundException("Не найдена книга для данного id: " + bookId);
        }
        return bookMapper.getCoverFromBookDto(kafkaRequestReplyById(bookDto));
    }

    /**
     * Метод выполняет пересылку объекта dto книги через Kafka и возвращает полученный из Kafka
     * объект dto книги.
     *
     * @param bookDto объект dto книги
     * @return объект dto книги
     */
    private BookDto kafkaRequestReplyById(BookDto bookDto) throws Exception {
        log.info("Вызов метода kafkaRequestReplyById() класса BookServiceKafka с параметром "
                + "bookInfo = {}", bookDto);
        ProducerRecord<String, BookDto> record = new ProducerRecord<>(sendTopicsById, bookDto);
        RequestReplyFuture<String, BookDto, BookDto> replyFuture =
                templateById.sendAndReceive(record);
//        SendResult<String, BookDto> sendResult = replyFuture
//                .getSendFuture()
//                .get(60, TimeUnit.SECONDS);
        ConsumerRecord<String, BookDto> consumerRecord = replyFuture
                .get(60, TimeUnit.SECONDS);
        BookDto result = consumerRecord.value();
        log.info("Получен ответ от library-service через Kafka в методе kafkaRequestReplyById() "
                + "класса BookServiceKafka с объектом {}", result);
        return result;
    }

    /**
     * Метод выполняет пересылку объекта dto книги через Kafka и возвращает полученный из Kafka
     * список объектов dto всех книг.
     *
     * @param bookDto объект dto книги
     * @return список объектов dto всех книг
     */
    private List<BookDto> kafkaRequestReplyAll(BookDto bookDto) throws Exception {
        log.info("Вызов метода kafkaRequestReplyAll() класса BookServiceKafka с параметром "
                + "bookInfo = {}", bookDto);
        ProducerRecord<String, BookDto> record = new ProducerRecord<>(sendTopicsAll, bookDto);
        RequestReplyFuture<String, BookDto, ListBookDto> replyFuture =
                templateAll.sendAndReceive(record);
        ConsumerRecord<String, ListBookDto> consumerRecord = replyFuture
                .get(60, TimeUnit.SECONDS);
        List<BookDto> bookDtoList = consumerRecord.value().getData();
        log.info("Получен ответ от library-service через Kafka в методе kafkaRequestReplyById() "
                + "класса BookServiceKafka с объектом {}", bookDtoList);
        return bookDtoList;
    }
}
