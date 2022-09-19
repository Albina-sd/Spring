package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.storage.BookRepository;
import com.edu.ulab.app.storage.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserRepositoryImpl implements UserRepository {
    final private BookRepository bookRepository;
    List<User> list = new ArrayList<>();

    public UserRepositoryImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public User findById(Long id) {
        return list.stream()
                .filter(Objects::nonNull)
                .filter(o -> o.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public User findByName(String fullName) {
        return list.stream()
                .filter(Objects::nonNull)
                .filter(o -> o.getFullName() == fullName)
                .findAny()
                .orElse(null);
    }

    @Override
    public User save(User u) {
        User user = new User();

        if (!list.isEmpty()) {
            long lastId = list.get(list.size() - 1).getId();
            user.setId(lastId + 1);
        } else {
            user.setId(1L);
        }

        user.setFullName(u.getFullName());
        user.setAge(u.getAge());
        user.setTitle(u.getTitle());

        list.add(user);

        return user;
    }

    @Override
    public void delete(Long id) {
        list.removeIf(x -> x.getId() == (id));
    }

    @Override
    public User update(User user) {
        int id = -1;

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getId() == (user.getId())) {
                id = i;
                break;
            }
        }

        if (id >= 0) {

            List<Book> books = bookRepository.findBooksByUserId(user.getId());
            User user1 = new User(user.getId(), user.getFullName(), user.getTitle(), user.getAge(), books);
            list.set(id, user);

            return user1;
        } else {
            return null;
        }
    }

}
