package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.User;

public interface UserRepository {
    User findById(Long id);

    User findByName(String fullName);

    User findByNameTitleAge(String fullName, String title, int age);

    User save(User user);

    void delete(Long id);

    User update(User user);
}

