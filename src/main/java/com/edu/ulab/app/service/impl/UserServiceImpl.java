package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.AlreadyExistsException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public UserDto createUser(UserDto userDto) {
        // сгенерировать идентификатор
        // создать пользователя
        // вернуть сохраненного пользователя со всеми необходимыми полями id

        //userDto.setId(1L);

        if (userRepository.findByName(userDto.getFullName()) != null){
            throw new AlreadyExistsException("User is already exist");
        }

        User user = userMapper.createUserMapping(userDto);
        userDto = userMapper.userResponse(userRepository.save(user));

        log.info("Service created user: {}", userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {


        long id = userDto.getId();
        User user = userRepository.findById(id);
        log.info("Update user: {}", user);
        UserDto userDto1 = userMapper.userResponse(userRepository.update(user));

        return userDto1;
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id);

        UserDto userDto = userMapper.userResponse(user);

        log.info("Getting user by id: {}", userDto);
        return userDto;
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("Delete user: {}", userRepository.findById(id));
        userRepository.delete(id);
    }
}
