package com.balenciaga.Browsers.Admin.Controller;

import com.balenciaga.Constants.API;
import com.balenciaga.DTO.Request.User.CreateUserRequest;
import com.balenciaga.DTO.Request.User.UserRequest;
import com.balenciaga.DTO.Response.User.UserResponse;
import com.balenciaga.Entities.User;
import com.balenciaga.DTO.Response.APIResponse;
import com.balenciaga.Services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(API.User.API_USER)
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<APIResponse<?>> getAllUser(@ModelAttribute UserRequest userRequest) {
        return ResponseEntity.ok(userService.getUser(userRequest));
    }

    @PostMapping("/new")
    public ResponseEntity<APIResponse<User>> createNewUser(@RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(userService.createUser(createUserRequest));
    }


    @GetMapping(API.User.API_USER_SEARCH_ID)
    public ResponseEntity<APIResponse<UserResponse>> getOneUser(@PathVariable String userID) {
        return ResponseEntity.ok(userService.getOneUser(userID));
    }


}
