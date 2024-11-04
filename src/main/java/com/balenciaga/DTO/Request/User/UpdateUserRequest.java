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
public class UpdateUserRequest {
    @NotEmpty(message = "First name is required")
    private String firstName;
    @NotEmpty(message = "Middle name is required")
    private String middleName;
    @NotEmpty(message = "Last name is required")
    private String lastName;
    @NotEmpty(message = "Email is required")
    private String email;
    @NotEmpty(message = "Phone number is required")
    private String phoneNumber;
    private boolean sex;
    @NotEmpty(message = "Birthday is required")
    private String birthday;
    @NotEmpty(message = "Password is required")
    private String password;
    private boolean status;
    @NotEmpty(message = "Role is required")
    private Set<Role> roles;
}
