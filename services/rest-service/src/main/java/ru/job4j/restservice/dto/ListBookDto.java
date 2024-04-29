package ru.job4j.restservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.job4j.restservice.wsdl.BookDto;

import java.util.List;

/**
 * Dto объект список dto книг
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListBookDto {

    /**
     * Список объектов dto книг
     */
    private List<BookDto> data;
}
