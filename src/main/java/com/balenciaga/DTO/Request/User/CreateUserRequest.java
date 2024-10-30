package com.balenciaga.DTO.Request.User;

import com.balenciaga.Entities.Role;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateUserRequest {
    @NotEmpty(message = "firstName is required not empty !")
    private String firstName;
    @NotEmpty(message = "middleName is required not empty !")
    private String middleName;
    @NotEmpty(message = "lastName is required not empty !")
    private String lastName;
    @NotEmpty(message = "email is required not empty !")
    private String email;
    @NotEmpty(message = "employeeCode is required not empty !")
    private String employeeCode;
    @NotEmpty(message = "phoneNumber is required not empty !")
    private String phoneNumber;
    private boolean sex;
    @NotEmpty(message = "birthday is required not empty !")
    private String birthday;
    @NotEmpty(message = "street is required not empty !")
    private String street;
    @NotEmpty(message = "ward is required not empty !")
    private String ward;
    @NotEmpty(message = "district is required not empty !")
    private String district;
    @NotEmpty(message = "city is required not empty !")
    private String city;
    @NotEmpty(message = "username is required not empty !")
    private String username;
    @NotEmpty(message = "password is required not empty !")
    private String password;
    private String photos;
    private boolean status;
    @NotEmpty(message = "roles is required not empty !")
    private Set<Role> roles;
}
