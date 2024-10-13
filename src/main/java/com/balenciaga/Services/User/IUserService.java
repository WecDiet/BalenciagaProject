package com.balenciaga.Services.User;

import com.balenciaga.DTO.Request.User.CreateUserRequest;
import com.balenciaga.DTO.Request.User.UpdateUserRequest;
import com.balenciaga.DTO.Request.User.UserRequest;
import com.balenciaga.DTO.Response.APIResponse;
import com.balenciaga.DTO.Response.User.UserResponse;
import com.balenciaga.Entities.User;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public interface IUserService {
    APIResponse<List<UserResponse>> getUser(@ModelAttribute UserRequest userRequest);
    APIResponse<User> createUser(CreateUserRequest createUserRequest);
    APIResponse<UserResponse> getOneUser (String userID);
    APIResponse<User> updateUser(UpdateUserRequest updateUserRequest);
}
