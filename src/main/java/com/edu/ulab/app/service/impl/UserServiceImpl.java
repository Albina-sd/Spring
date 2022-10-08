package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.AlreadyExistsException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UserServiceImpl
 *
 * Реализация сервиса UserService с помощью CRUD repository
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BookRepository bookRepository;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Person user = userMapper.userDtoToPerson(userDto);
        log.info("Mapped user: {}", user);

        Person duplicateUser = userRepository.findByFullName(user.getFullName()).orElse(null);
        if (duplicateUser != null && duplicateUser.getId() == null) {
            log.info("User with this full name: {} already exist", userRepository.findByFullName(user.getFullName()));
            throw new AlreadyExistsException("User with this full name already exist");
        }

        Person savedUser = userRepository.save(user);
        log.info("Saved user: {}", savedUser);
        return userMapper.personToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        Person user = userMapper.userDtoToPerson(userDto);
        log.info("Mapped user: {}", user);

        Person updatedUser = userRepository.findByFullName(userDto.getFullName()).orElse(null);
        log.info("Updating user: {}", updatedUser);

        if (updatedUser != null){

            user.setId(updatedUser.getId());
            log.info("Updated to: {}", user);
        }

        createUser(userMapper.personToUserDto(user));

        return userMapper.personToUserDto(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        Person user = userRepository.findById(id).orElse(null);
        user.setBookSet(bookRepository.findAllByPerson(user));
        log.info("Get user: {}", user);
        return userMapper.personToUserDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        // реализовать недстающие методы
        log.info("Delete user with id: {}", id);
        userRepository.deleteById(id);
    }
}
