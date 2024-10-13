package com.balenciaga.DTO.Request.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
