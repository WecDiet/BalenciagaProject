package com.balenciaga.DTO.Request.User;

import com.balenciaga.Entities.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean sex;
    private String birthday;
    private String username;
    private String password;
    private boolean status;
    private Set<Role> roles;
}
