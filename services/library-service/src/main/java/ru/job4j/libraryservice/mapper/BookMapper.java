package ru.job4j.libraryservice.mapper;

import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import ru.job4j.libraryservice.model.Book;
import ru.job4j.libraryservice.service.cover.CoverServiceMinio;
import ru.job4j.libraryservice.ws.BookDto;

/**
 * Класс выполняющий преобразование объектов Book в BookDto
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.libraryservice.model.Book
 * @see ru.job4j.libraryservice.ws.BookDto
 */
@Component
@AllArgsConstructor
public class BookMapper {

    /**
     * Объект для доступа к методам MinioService
     */
    private final CoverServiceMinio minioService;

    /**
     * Метод получает объект книги и возвращает преобразованный объект dto книги.
     *
     * @param book книга
     * @return dto книга
     */
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
