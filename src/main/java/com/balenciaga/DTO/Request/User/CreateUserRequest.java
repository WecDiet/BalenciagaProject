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
    @NotEmpty(message = "lastName is required not empty !")
    private String lastName;
    @NotEmpty(message = "email is required not empty !")
    private String email;
    @NotEmpty(message = "phoneNumber is required not empty !")
    private String phoneNumber;
    private boolean sex;
    @NotEmpty(message = "birthday is required not empty !")
    private String birthday;
    @NotEmpty(message = "username is required not empty !")
    private String username;
    @NotEmpty(message = "password is required not empty !")
    private String password;
    private String photos;
    private boolean status;
    @NotEmpty(message = "roles is required not empty !")
    private Set<Role> roles;
}
