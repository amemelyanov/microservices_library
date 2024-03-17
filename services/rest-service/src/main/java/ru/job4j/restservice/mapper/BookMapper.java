package ru.job4j.restservice.mapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import ru.job4j.restservice.model.Book;
import ru.job4j.restservice.wsdl.BookInfo;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BookMapper {

    public Book getBookFromBookInfo(BookInfo bookInfo) {
        log.info("Вызов метода getBookFromBookInfo() класса BookMapper с параметром bookInfo = {}", bookInfo);
        Book book = Book.builder()
                .id(bookInfo.getId())
                .name(bookInfo.getName())
                .description(bookInfo.getDescription())
                .author(bookInfo.getAuthor())
                .pages(bookInfo.getPages())
                .year(bookInfo.getYear())
                .build();
        return book;
    }

    public byte[] getCoverFromBookInfo(BookInfo bookInfo) {
        log.info("Вызов метода getCoverFromBookInfo() класса BookMapper с параметром bookInfo = {}", bookInfo);
        return Base64.decodeBase64(bookInfo.getCover());
    }

    public List<Book> getListBookFromListBookInfo(List<BookInfo> bookInfos) {
        log.info("Вызов метода getListBookFromListBookInfo() класса BookMapper с параметром bookInfos = {}", bookInfos);
        return bookInfos.stream()
                .map(bookInfo -> Book.builder()
                        .id(bookInfo.getId())
                        .name(bookInfo.getName())
                        .description(bookInfo.getDescription())
                        .author(bookInfo.getAuthor())
                        .pages(bookInfo.getPages())
                        .year(bookInfo.getYear())
                        .build()).collect(Collectors.toList());
    }
}
