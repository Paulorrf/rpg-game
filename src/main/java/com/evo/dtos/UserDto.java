package com.evo.dtos;
import com.evo.enums.Role;

public record UserDto(String name, String email, String password, Role role) {
}
