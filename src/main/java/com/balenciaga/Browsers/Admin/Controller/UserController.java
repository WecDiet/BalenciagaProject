package com.balenciaga.Browsers.Admin.Controller;

import com.balenciaga.Constants.Endpoint;
import com.balenciaga.DTO.Request.User.CreateUserRequest;
import com.balenciaga.DTO.Request.User.UpdateUserRequest;
import com.balenciaga.DTO.Request.User.UserMutiDeleteRequest;
import com.balenciaga.DTO.Request.User.UserRequest;
import com.balenciaga.DTO.Response.User.UserResponse;
import com.balenciaga.Entities.User;
import com.balenciaga.DTO.Response.APIResponse;
import com.balenciaga.Repositories.IUserRepository;
import com.balenciaga.Services.User.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping(Endpoint.User.BASE)
//@Validateds
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private IUserRepository IUserRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @GetMapping
    public ResponseEntity<APIResponse<?>> getAllUser(@ModelAttribute UserRequest userRequest) {
        logger.info("Get all user (API: http://localhost:8080/api/v1/admin/users)");
        return ResponseEntity.ok(userService.getUser(userRequest));
    }

    @PostMapping(Endpoint.User.NEW)
    public ResponseEntity<APIResponse<User>> createNewUser(@Valid @RequestBody CreateUserRequest createUserRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            // Creating an APIResponse with error messages
            APIResponse<User> errorResponse = new APIResponse<>(null, errorMessages);
            logger.error("Error creating new user: " + errorMessages);
            return ResponseEntity.badRequest().body(errorResponse);
        }
        return ResponseEntity.ok(userService.createUser(createUserRequest));
    }

    @GetMapping(Endpoint.User.ID)
    public ResponseEntity<APIResponse<UserResponse>> getOneUser(@PathVariable String userID) {
        if (userID.isEmpty()) {
            logger.error("Error getting user: ID is required, not empty!");
            return ResponseEntity.badRequest().body(new APIResponse<>(null, List.of("ID is required, not empty!")));
        }
        // Validate the UUID format
        UUID userUUID;
        try {
            logger.info("Get user by ID: " + userID);
            userUUID = UUID.fromString(userID);
        } catch (IllegalArgumentException e) {
            logger.error("Error getting user: ID is not valid!");
            return ResponseEntity.badRequest().body(new APIResponse<>(null, List.of("ID is not valid!")));
        }
        // Now proceed with finding the user
        Optional<User> userOptional = IUserRepository.findById(userUUID);
        if (userOptional.isEmpty()) {
            logger.error("Error getting user: User not found!");
            return ResponseEntity.badRequest().body(new APIResponse<>(null, List.of("User not found!")));
        }
        return ResponseEntity.ok(userService.getOneUser(userID));
    }

    @PutMapping(Endpoint.User.ID)
    public ResponseEntity<APIResponse<UserResponse>> updateUser(@Valid @RequestBody UpdateUserRequest userRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            // Creating an APIResponse with error messages
            APIResponse<UserResponse> errorResponse = new APIResponse<>(null, errorMessages);
            logger.error("Error updating user: " + errorMessages);
            return ResponseEntity.badRequest().body(errorResponse);
        }
        logger.info("Update user by ID");
        return ResponseEntity.ok(userService.updateUser(userRequest));
    }

    @DeleteMapping(Endpoint.User.ID)
    public ResponseEntity<APIResponse<Boolean>> deleteUser(@PathVariable String userID) {
        if (userID.isEmpty()) {
            logger.error("Error deleting user: ID is required, not empty!");
            return ResponseEntity.badRequest().body(new APIResponse<>(null, List.of("ID is required, not empty!")));
        }
        // Validate the UUID format
        UUID userUUID;
        try {
            logger.info("Format ID to UUID");
            userUUID = UUID.fromString(userID);
        } catch (IllegalArgumentException e) {
            logger.error("Error deleting user: ID is not valid!");
            return ResponseEntity.badRequest().body(new APIResponse<>(null, List.of("ID is not valid!")));
        }
        // Now proceed with finding the user
        Optional<User> userOptional = IUserRepository.findById(userUUID);
        if (userOptional.isEmpty()) {
            logger.error("Error deleting user: User not found!");
            return ResponseEntity.badRequest().body(new APIResponse<>(null, List.of("User not found!")));
        }
        logger.info("Delete user by ID");
        return ResponseEntity.ok(userService.deleteUser(userID));
    }

    @DeleteMapping(Endpoint.User.DELETE_MANY)
    public ResponseEntity<APIResponse<Boolean>> deleteMutiUser(@RequestBody UserMutiDeleteRequest userMutiDeleteRequest) {
        logger.info("Delete multiple users");
        return ResponseEntity.ok(userService.deleteMutiUser(userMutiDeleteRequest));
    }
}
