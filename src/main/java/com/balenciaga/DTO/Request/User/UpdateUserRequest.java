package com.balenciaga.DTO.Request.User;

import com.balenciaga.Entities.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean sex;
    private String birthday;
    private String password;
    private boolean status;
    private Set<Role> roles;
}
