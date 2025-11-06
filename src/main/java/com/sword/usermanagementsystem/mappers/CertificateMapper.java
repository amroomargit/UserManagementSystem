package com.sword.usermanagementsystem.mappers;

import com.sword.usermanagementsystem.dtos.CertificateDTO;
import com.sword.usermanagementsystem.entities.Certificate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CourseMapper.class, StudentMapper.class})
public interface CertificateMapper {

    //Entity to DTO
    CertificateDTO toDTO (Certificate certificate);

    //DTO to Entity
    Certificate toEntity (CertificateDTO certificateDTO);

}
