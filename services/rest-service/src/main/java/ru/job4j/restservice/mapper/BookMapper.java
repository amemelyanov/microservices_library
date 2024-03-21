package ru.job4j.restservice.mapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import ru.job4j.restservice.model.Book;
import ru.job4j.restservice.wsdl.BookDto;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BookMapper {

    public Book getBookFromBookDto(BookDto bookDto) {
        log.info("Вызов метода getBookFromBookInfo() класса BookMapper с параметром bookInfo = {}", bookDto);
        Book book = Book.builder()
                .id(bookDto.getId())
                .name(bookDto.getName())
                .description(bookDto.getDescription())
                .author(bookDto.getAuthor())
                .pages(bookDto.getPages())
                .year(bookDto.getYear())
                .build();
        return book;
    }

    public byte[] getCoverFromBookDto(BookDto bookDto) {
        log.info("Вызов метода getCoverFromBookInfo() класса BookMapper с параметром bookInfo = {}", bookDto);
        return Base64.decodeBase64(bookDto.getCover());
    }

    public List<Book> getListBookFromListBookDto(List<BookDto> bookDtoList) {
        log.info("Вызов метода getListBookFromListBookInfo() класса BookMapper с параметром bookInfos = {}", bookDtoList);
        return bookDtoList.stream()
                .map(bookDto -> Book.builder()
                        .id(bookDto.getId())
                        .name(bookDto.getName())
                        .description(bookDto.getDescription())
                        .author(bookDto.getAuthor())
                        .pages(bookDto.getPages())
                        .year(bookDto.getYear())
                        .build()).collect(Collectors.toList());
    }
}
