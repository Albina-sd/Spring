package com.edu.ulab.app.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserDto {
    private Long id;

    @NotBlank(message = "full name cannot be empty")
    private String fullName;

    @NotBlank(message = "title cannot be empty")
    private String title;

    @NotNull(message = "age cannot be null")
    private int age;
}
