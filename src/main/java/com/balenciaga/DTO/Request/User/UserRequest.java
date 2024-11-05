package com.balenciaga.DTO.Request.User;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private int limit= -1;
    private int page = -1;
    @NotEmpty(message = "Full name is required")
    private String fullName;
    @NotEmpty(message = "Employee code is required")
    private String employeeCode;
    @NotEmpty(message = "Role name is required")
    private String role;
    @NotEmpty(message = "Email is required")
    private String email;
}
