package ru.job4j.libraryservice.service.consumer;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.job4j.libraryservice.service.BookService;
import ru.job4j.libraryservice.service.producer.BookProducer;
import ru.job4j.libraryservice.ws.BookInfo;
import ru.job4j.libraryservice.ws.FindBookByIdRequest;

import java.util.List;

@AllArgsConstructor
@Service
public class BookKafkaConsumer implements BookConsumer {

    private final BookService bookService;
    private final BookProducer bookProducer;

    @KafkaListener(topics = "findBookByIdRequest",
            containerFactory = "findByIdBookresponseKafkaListenerContainerFactory")
    public void findBookByIdListener(FindBookByIdRequest request) {
        BookInfo bookInfo = bookService.findById(request.getBookId());
//        bookProducer.sendBookInfo(bookInfo);
    }

    @KafkaListener(topics = "findAllBooksRequest",
            containerFactory = "findAllBooksRequestKafkaListenerContainerFactory")
    public void findAllBooksListener() {
        List<BookInfo> books = bookService.findAll();
//        bookProducer.sendBooks(books);
    }

}
