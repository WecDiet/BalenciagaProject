package com.balenciaga.DTO.Response.User;


import com.balenciaga.Entities.Role;
import lombok.*;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean sex;
    private String birthday;
    private String username;
    private String password;
    private String photos;
    private boolean status;
//    private List<Role> roles;
    private Role role;
}
