package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.storage.BookRepositoryWithMap;
import com.edu.ulab.app.storage.UserRepositoryWithMap;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Имплементация репозитория
 * для сущности пользователь
 */

@Repository
public class UserRepositoryImpl implements UserRepositoryWithMap {
    final private BookRepositoryWithMap bookRepository;
    private Map<Person, Long> map = new HashMap<>();

    public UserRepositoryImpl(BookRepositoryWithMap bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Person findById(Long id) {
        return map.keySet().stream()
                .filter(Objects::nonNull)
                .filter(o -> o.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public Person findByName(String fullName) {
        return map.keySet().stream()
                .filter(Objects::nonNull)
                .filter(o -> o.getFullName() == fullName)
                .findAny()
                .orElse(null);
    }

    @Override
    public Person findByNameTitleAge(String fullName, String title, int age) {
        return map.keySet().stream()
                .filter(Objects::nonNull)
                .filter(o -> o.getFullName() == fullName)
                .filter(o -> o.getAge() == age)
                .filter(o -> o.getTitle() == title)
                .findAny()
                .orElse(null);
    }

    @Override
    public Person save(Person u) {
//        User user = new User();
//        user.setFullName(u.getFullName());
//        user.setAge(u.getAge());
//        user.setTitle(u.getTitle());

        if (!map.isEmpty()){
            long lastId = Collections.max(map.values());
            u.setId(lastId + 1);
        } else {
            u.setId(1L);
        }

        map.put(u, u.getId());

        return u;
    }

    @Override
    public void delete(Long id) {
        Person user = findById(id);
        map.remove(user, id);
    }

    @Override
    public Person update(Person user) {
        Person oldUser = findById(user.getId());
        map.remove(oldUser);
        map.put(user, user.getId());

        return user;
    }

}
