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

@Service
@Slf4j
@NoArgsConstructor
public class BookServiceKafka implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ReplyingKafkaTemplate<String, BookDto, BookDto> templateById;

    @Autowired
    private ReplyingKafkaTemplate<String, BookDto, ListBookDto> templateAll;

    @Value("${library-project.send-topics-by-id}")
    private String sendTopicsById;

    @Value("${library-project.send-topics-all}")
    private String sendTopicsAll;

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

    @SneakyThrows
    @Override
    public List<Book> findAll() {
        log.info("Вызов метода findAll() класса BookServiceKafka");
        BookDto bookDto = new BookDto();
        bookDto.setId(0);
        return bookMapper.getListBookFromListBookDto(kafkaRequestReplyAll(bookDto));
    }

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

    private BookDto kafkaRequestReplyById(BookDto bookDto) throws Exception {
        log.info("Вызов метода kafkaRequestReplyById() класса BookServiceKafka с параметром "
                + "bookInfo = {}", bookDto);
        ProducerRecord<String, BookDto> record = new ProducerRecord<>(sendTopicsById, bookDto);
        RequestReplyFuture<String, BookDto, BookDto> replyFuture =
                templateById.sendAndReceive(record);
        SendResult<String, BookDto> sendResult = replyFuture
                .getSendFuture()
                .get(60, TimeUnit.SECONDS);
        ConsumerRecord<String, BookDto> consumerRecord = replyFuture
                .get(60, TimeUnit.SECONDS);
        BookDto result = consumerRecord.value();
        log.info("Получен ответ от library-service через Kafka в методе kafkaRequestReplyById() "
                + "класса BookServiceKafka с объектом {}", result);
        return result;
    }

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
