package ru.job4j.restservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Модель данных книга
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Builder
@ToString
@EqualsAndHashCode(of = "id")
@Data
public class Book {

    /**
     * Идентификатор
     */
    private long id;

    /**
     * Название
     */
    private String name;

    /**
     * Описание
     */
    private String description;

    /**
     * Автор
     */
    private String author;

    /**
     * Количество страниц
     */
    private int pages;

    /**
     * Годы выпуска
     */
    private int year;

}
