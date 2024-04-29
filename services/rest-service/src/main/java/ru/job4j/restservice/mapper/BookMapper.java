package ru.job4j.restservice.mapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import ru.job4j.restservice.model.Book;
import ru.job4j.restservice.wsdl.BookDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Компонент выполняющий преобразование объектов
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.restservice.model.Book
 * @see ru.job4j.restservice.wsdl.BookDto
 * @see ru.job4j.restservice.dto.ListBookDto
 */
@Slf4j
@Component
public class BookMapper {

    /**
     * Метод получает объект dto книги и возвращает преобразованный объект книги.
     *
     * @param bookDto объект dto книги
     * @return книга
     */
    public Book getBookFromBookDto(BookDto bookDto) {
        log.info("Вызов метода getBookFromBookInfo() класса BookMapper с параметром bookInfo = {}", bookDto);
        return Book.builder()
                .id(bookDto.getId())
                .name(bookDto.getName())
                .description(bookDto.getDescription())
                .author(bookDto.getAuthor())
                .pages(bookDto.getPages())
                .year(bookDto.getYear())
                .build();
    }

    /**
     * Метод получает объект dto книги и возвращает байтовый массив содержащий картинку обложки книги.
     *
     * @param bookDto объект dto книги
     * @return байтовый массив картинки обложки книги
     */
    public byte[] getCoverFromBookDto(BookDto bookDto) {
        log.info("Вызов метода getCoverFromBookInfo() класса BookMapper с параметром bookInfo = {}", bookDto);
        return Base64.decodeBase64(bookDto.getCover());
    }

    /**
     * Метод получает список объектов dto всех книг и возвращает список всех книг.
     *
     * @param bookDtoList список объектов dto всех книг
     * @return список всех книг
     */
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
