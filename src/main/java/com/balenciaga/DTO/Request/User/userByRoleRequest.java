package com.balenciaga.DTO.Request.User;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class userByRoleRequest {
    private int limit= -1;
    private int page = -1;
    @NotEmpty(message = "Full name is required")
    private String fullName;
    @NotEmpty(message = "Employee code is required")
    private String employeeCode;
    @NotEmpty(message = "Role name is required")
    private String roleName;
}
