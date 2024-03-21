package ru.job4j.restservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode(of = "id")
@Data
public class Book {

    private Long id;
    private String name;
    private String description;
    private String author;
    private int pages;
    private int year;

}
