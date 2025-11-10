package com.sword.usermanagementsystem.mappers;

import com.sword.usermanagementsystem.dtos.AdminDTO;
import com.sword.usermanagementsystem.entities.Admin;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    //Entity to DTO
    AdminDTO toDTO (Admin admin);

    //DTO to Entity
    Admin toEntity (AdminDTO adminDTO);


    //Entities to DTOs
    List<AdminDTO> toDTOs (List<Admin> admin);

    //DTOs to Entities
    List<Admin> toEntities (List<AdminDTO> adminDTO);
}
