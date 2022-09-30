package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Person user = userMapper.userDtoToPerson(userDto);
        log.info("Mapped user: {}", user);
        Person savedUser = userRepository.save(user);
        log.info("Saved user: {}", savedUser);
        return userMapper.personToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        // реализовать недстающие методы
        Person user = userMapper.userDtoToPerson(userDto);
        log.info("Mapped user: {}", user);

        Person updatedUser = userRepository.findByFullName(user.getFullName()).orElse(null);
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
        // реализовать недстающие методы
        Person user = userRepository.findById(id).orElse(null);
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
