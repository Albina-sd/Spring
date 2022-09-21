package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.storage.BookRepository;
import com.edu.ulab.app.storage.UserRepository;
import org.springframework.stereotype.Repository;


/**
 * Имплементация репозитория
 * для сущности пользователь
 */

import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {
    final private BookRepository bookRepository;
    private Map<User, Long> map = new HashMap<>();

    public UserRepositoryImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public User findById(Long id) {
        return map.keySet().stream()
                .filter(Objects::nonNull)
                .filter(o -> o.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public User findByName(String fullName) {
        return map.keySet().stream()
                .filter(Objects::nonNull)
                .filter(o -> o.getFullName() == fullName)
                .findAny()
                .orElse(null);
    }

    @Override
    public User findByNameTitleAge(String fullName, String title, int age) {
        return map.keySet().stream()
                .filter(Objects::nonNull)
                .filter(o -> o.getFullName() == fullName)
                .filter(o -> o.getAge() == age)
                .filter(o -> o.getTitle() == title)
                .findAny()
                .orElse(null);
    }

    @Override
    public User save(User u) {
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
        User user = findById(id);
        map.remove(user, id);
    }

    @Override
    public User update(User user) {
        User oldUser = findById(user.getId());
        map.remove(oldUser);
        map.put(user, user.getId());

        return user;
    }

}
