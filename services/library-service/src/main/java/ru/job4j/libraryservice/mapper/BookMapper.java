package ru.job4j.libraryservice.mapper;

import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import ru.job4j.libraryservice.model.Book;
import ru.job4j.libraryservice.service.MinioService;
import ru.job4j.libraryservice.ws.BookInfo;

@Component
@AllArgsConstructor
public class BookMapper {

    private final MinioService minioService;

    public BookInfo getBookInfoFromBook(Book book) {
        byte[] cover = minioService.getFile(book.getCover()).orElse(new byte[0]);
        String coverAsString = Base64.encodeBase64String(cover);
        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(book.getId());
        bookInfo.setName(book.getName());
        bookInfo.setDescription(book.getDescription());
        bookInfo.setAuthor(book.getAuthor());
        bookInfo.setPages(book.getPages());
        bookInfo.setYear(book.getYear());
        bookInfo.setCover(coverAsString);
        return bookInfo;
    }
}
