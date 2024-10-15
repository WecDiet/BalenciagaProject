package com.balenciaga.Browsers.Admin.Controller;

import com.balenciaga.Constants.Endpoint;
import com.balenciaga.DTO.Request.User.CreateUserRequest;
import com.balenciaga.DTO.Request.User.UpdateUserRequest;
import com.balenciaga.DTO.Request.User.UserRequest;
import com.balenciaga.DTO.Response.User.UserResponse;
import com.balenciaga.Entities.User;
import com.balenciaga.DTO.Response.APIResponse;
import com.balenciaga.Services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(Endpoint.User.BASE)
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<APIResponse<?>> getAllUser(@ModelAttribute UserRequest userRequest) {
        return ResponseEntity.ok(userService.getUser(userRequest));
    }

    @PostMapping(Endpoint.User.NEW)
    public ResponseEntity<APIResponse<User>> createNewUser(@RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(userService.createUser(createUserRequest));
    }


    @GetMapping(Endpoint.User.ID)
    public ResponseEntity<APIResponse<UserResponse>> getOneUser(@PathVariable String userID) {
        return ResponseEntity.ok(userService.getOneUser(userID));
    }


    @PutMapping(Endpoint.User.ID)
    public ResponseEntity<APIResponse<UserResponse>> updateUser(@RequestBody UpdateUserRequest userRequest) {
        return ResponseEntity.ok(userService.updateUser(userRequest));
    }
}
