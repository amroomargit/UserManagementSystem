package com.sword.usermanagementsystem.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminDTO extends UserDTO{
    private int id;
    private String firstName;
    private String lastName;

    private UserDTO user;

}
