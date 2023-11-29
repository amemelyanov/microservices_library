package ru.job4j.restservice.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.job4j.restservice.wsdl.BookInfo;
import ru.job4j.restservice.wsdl.FindAllBooksRequest;
import ru.job4j.restservice.wsdl.FindBookByIdRequest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private BookClient bookClient;
    private KafkaTemplate<Long, FindBookByIdRequest> findBookByIdRequestKafkaTemplate;
    private KafkaTemplate<Long, FindAllBooksRequest> findAllBooksRequestKafkaTemplate;

    @Override
    public BookInfo findById(Long id) {
//        FindBookByIdResponse response = bookClient.findBookById(id);
        FindBookByIdRequest request = new FindBookByIdRequest();
        request.setBookId(id);
        try {
            CompletableFuture<SendResult<Long, FindBookByIdRequest>> future =  findBookByIdRequestKafkaTemplate.send(
                    "findBookByIdRequest", id, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BookInfo> findAll() {
//        FindAllBooksResponse allBooks = bookClient.findAllBooks();
        FindAllBooksRequest request = new FindAllBooksRequest();
        try {
            CompletableFuture<SendResult<Long, FindAllBooksRequest>> future =  findAllBooksRequestKafkaTemplate.send(
                    "findAllBooksRequest", request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
//        return allBooks.getBookInfo();
    }
}
