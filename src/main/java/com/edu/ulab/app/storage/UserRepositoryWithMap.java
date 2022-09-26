package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.Person;

public interface UserRepositoryWithMap {
    Person findById(Long id);

    Person findByName(String fullName);

    Person findByNameTitleAge(String fullName, String title, int age);

    Person save(Person user);

    void delete(Long id);

    Person update(Person user);
}

