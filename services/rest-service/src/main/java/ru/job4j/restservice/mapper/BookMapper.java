package ru.job4j.restservice.mapper;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import ru.job4j.restservice.model.Book;
import ru.job4j.restservice.wsdl.BookInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public Book getBookFromBookInfo(BookInfo bookInfo) {
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
        return Base64.decodeBase64(bookInfo.getCover());
    }

    public List<Book> getListBookFromListBookInfo(List<BookInfo> bookInfos) {
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
