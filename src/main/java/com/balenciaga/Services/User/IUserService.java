package com.balenciaga.Services.User;

import com.balenciaga.DTO.Request.User.*;
import com.balenciaga.DTO.Response.APIResponse;
import com.balenciaga.DTO.Response.PagingResponse;
import com.balenciaga.DTO.Response.User.UserResponse;
import com.balenciaga.Entities.User;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IUserService {
    PagingResponse<List<UserResponse>> getUser(@ModelAttribute UserRequest userRequest);
    APIResponse<User> createUser(CreateUserRequest createUserRequest);
    APIResponse<UserResponse> getOneUser (String userID);
    APIResponse<UserResponse> updateUser(UpdateUserRequest updateUserRequest);
    APIResponse<Boolean> deleteUser(String userID);
    APIResponse<Boolean> deleteMutiUser(UserMutiDeleteRequest userMutiDeleteRequest);
    //PagingResponse<List<UserResponse>> getUserByRole(@ModelAttribute userByRoleRequest userByRoleRequest);
}
