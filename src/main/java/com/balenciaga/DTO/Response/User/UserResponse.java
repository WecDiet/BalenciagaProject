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
    private String middleName;
    private String lastName;
    private String fullName;
    private String email;
    private String employeeCode;
    private String phoneNumber;
    private boolean sex;
    private String street;
    private String ward;
    private String district;
    private String city;
    private String birthday;
    private String username;
    private String password;
    private String photos;
    private boolean status;
    private List<Role> roles;
}
