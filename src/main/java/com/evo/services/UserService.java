package com.evo.services;

import com.evo.dtos.UserDto;
import com.evo.enums.Role;
import com.evo.mapper.UserMapper;
import com.evo.models.User;
import com.evo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository repository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserDto save(UserDto userDto) {
        User userAlreadyExist = repository.findByEmail(userDto.email());

        if(userAlreadyExist != null) {
            throw new RuntimeException("Usuário já existe");
        }

        User user = userMapper.userDTOtoUser(userDto);

        //Encrypt password
        var passwordHashed = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHashed);

        //every user created will only have USER permission, unless created in the db
        user.setRole(Role.USER);

        if(user != null) {
            User savedUser = repository.save(user);

            return userMapper.userToUserDTO(savedUser);
        }

        return null;
    }
}
