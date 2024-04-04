package ru.job4j.libraryservice.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Модель данных книга
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Entity
@Table(name = "book")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    /**
     * Наименование обложки
     */
    private String coverName;

}
