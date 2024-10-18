package com.balenciaga.DTO.Request.User;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserRequest {
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
}
