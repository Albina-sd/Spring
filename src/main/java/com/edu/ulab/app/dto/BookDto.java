package com.edu.ulab.app.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BookDto {
    private Long id;

    @NotNull(message = "user Id cannot be null")
    private Long userId;

    @NotBlank(message = "title cannot be empty")
    private String title;

    @NotBlank(message = "author cannot be empty")
    private String author;

    @NotBlank(message = "page Count cannot be empty")
    private long pageCount;
}