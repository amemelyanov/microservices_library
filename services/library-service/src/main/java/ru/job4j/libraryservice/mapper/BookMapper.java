package ru.job4j.libraryservice.mapper;

import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import ru.job4j.libraryservice.model.Book;
import ru.job4j.libraryservice.service.MinioService;
import ru.job4j.libraryservice.ws.BookDto;

@Component
@AllArgsConstructor
public class BookMapper {

    private final MinioService minioService;

    public BookDto getBookDtoFromBook(Book book) {
        byte[] cover = minioService.getFile(book.getCoverName()).orElse(new byte[0]);
        String coverAsString = Base64.encodeBase64String(cover);
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setDescription(book.getDescription());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPages(book.getPages());
        bookDto.setYear(book.getYear());
        bookDto.setCover(coverAsString);
        return bookDto;
    }
}
