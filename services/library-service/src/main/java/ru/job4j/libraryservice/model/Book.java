package ru.job4j.libraryservice.model;

import jakarta.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String description;
    private String author;
    private int pages;
    private int year;
}
