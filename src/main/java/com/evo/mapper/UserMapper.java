package com.evo.mapper;

import com.evo.dtos.UserDto;
import com.evo.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;



@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    UserDto userToUserDTO(User user);

    User userDTOtoUser(UserDto userDTO);
}
