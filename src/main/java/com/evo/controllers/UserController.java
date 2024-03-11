package com.evo.controllers;

import com.evo.dtos.UserDto;
import com.evo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public UserDto save(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "Ok";
    }

    @GetMapping("/usuario")
    public String getUsuario() {
        return "Ok";
    }
}
