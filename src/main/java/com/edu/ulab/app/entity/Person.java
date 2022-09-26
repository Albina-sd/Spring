package com.edu.ulab.app.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "PERSON")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String title;
    private int age;
}
