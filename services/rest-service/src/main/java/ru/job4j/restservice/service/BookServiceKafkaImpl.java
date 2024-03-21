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
import ru.job4j.restservice.mapper.BookMapper;
import ru.job4j.restservice.model.KafkaMessage;
import ru.job4j.restservice.wsdl.BookInfo;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@NoArgsConstructor
public class BookServiceKafkaImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ReplyingKafkaTemplate<String, BookInfo, BookInfo> templateById;

    @Autowired
    private ReplyingKafkaTemplate<String, BookInfo, KafkaMessage> templateAll;

    @Value("${library-project.send-topics-by-id}")
    private String sendTopicsById;

    @Value("${library-project.send-topics-all}")
    private String sendTopicsAll;

    @SneakyThrows
    @Override
    public BookInfo findById(Long bookId) {
        log.info("Вызов метода findById() класса BookServiceKafkaImpl с параметром bookId = {}", bookId);
        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(bookId);
        return kafkaRequestReplyById(bookInfo);
    }

    @SneakyThrows
    @Override
    public List<BookInfo> findAll() {
        log.info("Вызов метода findAll() класса BookServiceKafkaImpl");
        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(0);
        return kafkaRequestReplyAll(bookInfo);
    }

    private BookInfo kafkaRequestReplyById(BookInfo bookInfo) throws Exception {
        log.info("Вызов метода kafkaRequestReplyById() класса BookServiceKafkaImpl с параметром "
                + "bookInfo = {}", bookInfo);
        ProducerRecord<String, BookInfo> record = new ProducerRecord<>(sendTopicsById, bookInfo);
        RequestReplyFuture<String, BookInfo, BookInfo> replyFuture =
                templateById.sendAndReceive(record);
        SendResult<String, BookInfo> sendResult = replyFuture
                .getSendFuture()
                .get(60, TimeUnit.SECONDS);
        ConsumerRecord<String, BookInfo> consumerRecord = replyFuture
                .get(60, TimeUnit.SECONDS);
        BookInfo result = consumerRecord.value();
        log.info("Получен ответ от library-service через Kafka в методе kafkaRequestReplyById() "
                + "класса BookServiceKafkaImpl с объектом {}", result);
        return result;
    }

    private List<BookInfo> kafkaRequestReplyAll(BookInfo bookInfo) throws Exception {
        log.info("Вызов метода kafkaRequestReplyAll() класса BookServiceKafkaImpl с параметром "
                + "bookInfo = {}", bookInfo);
        ProducerRecord<String, BookInfo> record = new ProducerRecord<>(sendTopicsAll, bookInfo);
        RequestReplyFuture<String, BookInfo, KafkaMessage> replyFuture =
                templateAll.sendAndReceive(record);
        ConsumerRecord<String, KafkaMessage> consumerRecord = replyFuture
                .get(60, TimeUnit.SECONDS);
        List<BookInfo> bookInfos = consumerRecord.value().getData();
        log.info("Получен ответ от library-service через Kafka в методе kafkaRequestReplyById() "
                + "класса BookServiceKafkaImpl с объектом {}", bookInfos);
        return bookInfos;
    }
}
