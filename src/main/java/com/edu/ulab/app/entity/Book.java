package com.edu.ulab.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * книга
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
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

