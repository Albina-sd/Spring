package com.edu.ulab.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Пользователь с книгами
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Long id;
    private String fullName;
    private String title;
    private int age;
    private List<Book> books = new ArrayList<>();
}
