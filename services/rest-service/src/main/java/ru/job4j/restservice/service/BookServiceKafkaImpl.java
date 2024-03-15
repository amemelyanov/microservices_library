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
import ru.job4j.restservice.model.Book;
import ru.job4j.restservice.model.KafkaMessage;
import ru.job4j.restservice.wsdl.BookInfo;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@NoArgsConstructor
public class BookServiceKafkaImpl implements BookService{

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
    public BookInfo findById(Long id) {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(id);
        return kafkaRequestReplyById(bookInfo);
    }

    @SneakyThrows
    @Override
    public List<BookInfo> findAll() {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(0);
        return kafkaRequestReplyAll(bookInfo);
    }

    private BookInfo kafkaRequestReplyById(BookInfo bookInfo) throws Exception {
        ProducerRecord<String, BookInfo> record = new ProducerRecord<>(sendTopicsById, bookInfo);
        RequestReplyFuture<String, BookInfo, BookInfo> replyFuture =
                templateById.sendAndReceive(record);
        SendResult<String, BookInfo> sendResult = replyFuture
                .getSendFuture()
                .get(60, TimeUnit.SECONDS);
        ConsumerRecord<String, BookInfo> consumerRecord = replyFuture
                .get(60, TimeUnit.SECONDS);
        return consumerRecord.value();
    }

    private List<BookInfo> kafkaRequestReplyAll(BookInfo bookInfo) throws Exception {
        ProducerRecord<String, BookInfo> record = new ProducerRecord<>(sendTopicsAll, bookInfo);
        RequestReplyFuture<String, BookInfo, KafkaMessage> replyFuture =
                templateAll.sendAndReceive(record);
        ConsumerRecord<String, KafkaMessage> consumerRecord = replyFuture
                .get(60, TimeUnit.SECONDS);
        return consumerRecord.value().getData();
    }
}
