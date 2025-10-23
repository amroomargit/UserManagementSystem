package com.sword.usermanagementsystem.mappers;

import org.mapstruct.Mapper;
import com.sword.usermanagementsystem.entities.User;
import com.sword.usermanagementsystem.dtos.UserDTO;

@Mapper(componentModel = "spring", uses = {TeacherMapper.class, StudentMapper.class})
public interface UserMapper {

    //Entity to DTO
    UserDTO toDTO(User user);

    //DTO to Entity
    User toEntity(UserDTO userDTO);
}
