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
    private String fullName;
    private String employeeCode;
}
