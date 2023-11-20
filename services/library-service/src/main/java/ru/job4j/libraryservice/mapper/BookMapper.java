package ru.job4j.libraryservice.mapper;

import org.mapstruct.Mapper;
import ru.job4j.libraryservice.model.Book;
import ru.job4j.libraryservice.ws.BookInfo;

@Mapper(componentModel = "spring")
public interface BookMapper {
   BookInfo getBookInfoFromBook(Book book);
}
