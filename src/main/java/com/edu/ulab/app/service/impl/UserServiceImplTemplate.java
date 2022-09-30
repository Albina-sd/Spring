package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.mapper.MapperForUser;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;

/**
 * UserServiceImplTemplate
 *
 * Реализация сервиса для пользователей с помощью JDBC Template
 */

@Slf4j
@Service
public class UserServiceImplTemplate implements UserService {
    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    public UserServiceImplTemplate(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        final String INSERT_SQL = "INSERT INTO PERSON(FULL_NAME, TITLE, AGE) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                    ps.setString(1, userDto.getFullName());
                    ps.setString(2, userDto.getTitle());
                    ps.setLong(3, userDto.getAge());
                    return ps;
                }, keyHolder);

        userDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return userDto;
    }

    /**
     * updateUser
     *
     * обновляет записть пользователя
     * @param userDto
     * @return userDto возвращает обновленного пользователя
     */

    @Override
    public UserDto updateUser(UserDto userDto) {
        // реализовать недстающие методы

        final String UPDATE_SQL = "UPDATE PERSON " +
                "SET TITLE = ?, AGE = ?" +
                "WHERE ID = ?";

        Person user = getByFullName(userDto).orElse(null);

        if (user != null) {
            log.info("Updated user: {}", user);

            jdbcTemplate.update(UPDATE_SQL, userDto.getTitle(), userDto.getAge(), user.getId());
            userDto.setId(user.getId());

            log.info("To user: {}", userDto);
        } else {
            log.info("User with full name {} not found", userDto.getFullName());
            createUser(userDto);
        }

        return userDto;
    }

    /**
     * Вспомогательный метод для нахождения пользователя для обновления
     *
     * Нахождение производится по full name
     * @param userDto
     * @return userDto - найденный пользователь или пустое значение
     */
    private Optional<Person> getByFullName(UserDto userDto){
        final String SELECT_SQL = "SELECT * FROM PERSON WHERE FULL_NAME = ?";

        try{
            Person user = jdbcTemplate.queryForObject(SELECT_SQL,
                    new Object[]{userDto.getFullName()},
                    new MapperForUser());
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e){
            log.debug("No record found in database for full name" + userDto.getFullName(), e);
            return Optional.empty();
        }
    }

    @Override
    public UserDto getUserById(Long id) {
        // реализовать недстающие методы

        final String SELECT_SQL = "SELECT * FROM PERSON WHERE ID = ?";

        Person user = jdbcTemplate.queryForObject(SELECT_SQL,
                new Object[]{id},
                new MapperForUser());

        log.info("Getting user: {}", user);
        return userMapper.personToUserDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        // реализовать недстающие методы

        final String DELETE_SQL = "DELETE FROM PERSON WHERE ID = ?";

        jdbcTemplate.update(DELETE_SQL, id);
        log.info("Deleting user with id: {}", id);
    }
}
