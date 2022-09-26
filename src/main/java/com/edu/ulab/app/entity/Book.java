package com.edu.ulab.app.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * книга
 */

@Entity
@Data
@Table(name = "BOOK")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String title;
    private String author;
    private long pageCount;

}

