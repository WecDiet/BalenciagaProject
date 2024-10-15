package com.balenciaga.Services.User;

import com.balenciaga.Config.Validate;
import com.balenciaga.DTO.Request.User.CreateUserRequest;
import com.balenciaga.DTO.Request.User.UpdateUserRequest;
import com.balenciaga.DTO.Request.User.UserRequest;
import com.balenciaga.Repositories.IRoleRepository;
import com.balenciaga.DTO.Response.APIResponse;
import com.balenciaga.DTO.Response.User.UserResponse;
import com.balenciaga.Constants.MessageKey;
import com.balenciaga.Entities.Role;
import com.balenciaga.Entities.User;
import com.balenciaga.Repositories.IUserRepository;
import com.balenciaga.Utils.LocalizationUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IUserRepository IUserRepository;
    @Autowired
    private LocalizationUtil localizationUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRoleRepository IRoleRepository;

    @Override
    public APIResponse<List<UserResponse>> getUser(@ModelAttribute UserRequest userRequest) {
        ArrayList<UserResponse> userResponseList; // danh sách chưa các phản hồi của user
        List<User> userList; // danh sách chưa danh sách user
        userList = IUserRepository.findAll();
        userResponseList = new ArrayList<>(userList.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList());
        return new APIResponse<>(userResponseList, localizationUtil.getLocalizedMessage(MessageKey.USER_GET_SUCCESS));
    }

    @Override
    public APIResponse<User> createUser(CreateUserRequest createUserRequest) {
        if(IUserRepository.existsByEmail(createUserRequest.getEmail())) {
            return new APIResponse<>(null,localizationUtil.getLocalizedMessage(MessageKey.USER_ALREADY_EXIST));
        }
        if(IUserRepository.existsByPhoneNumber(createUserRequest.getPhoneNumber())){
            return new APIResponse<>(null, localizationUtil.getLocalizedMessage(MessageKey.USER_PHONE_EXISTED));
        }
        User userCreated = modelMapper.map(createUserRequest, User.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            userCreated.setBirthday(dateFormat.parse(createUserRequest.getBirthday()));
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format. Expected format: yyyy-MM-dd");
        }
        userCreated.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        Set<Role> managedRoles = new HashSet<>();
        for (Role role : createUserRequest.getRoles()) {
            Role managedRole = IRoleRepository.findByName(role.getName())
                    .orElseThrow(() -> new RuntimeException("Role not found: " + role));
            managedRoles.add(managedRole);
        }
        userCreated.setRoles(managedRoles);
        IUserRepository.save(userCreated);
        return new APIResponse<>(userCreated, localizationUtil.getLocalizedMessage(MessageKey.USER_CREATE_SUCCESS));
    }

    @Override
    public APIResponse<UserResponse> getOneUser(String userID) {
        if(!Validate.isValidUUID(userID)) {
            throw new RuntimeException("Invalid UUID string: " + userID);
        }
        User user = IUserRepository.findById(UUID.fromString(userID))
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userID));
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return new APIResponse<>(userResponse, localizationUtil.getLocalizedMessage(MessageKey.USER_GET_ONE_SUCCESS));
    }

    @Override
    public APIResponse<UserResponse> updateUser(UpdateUserRequest updateUserRequest) {
        User user = IUserRepository.findByEmail(updateUserRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + updateUserRequest.getEmail()));
        System.out.println(updateUserRequest.getEmail());
        modelMapper.map(updateUserRequest, user);
        if (updateUserRequest.getRoles() != null) {
            Set<Role> updatedRoles = new HashSet<>();
            for (Role role : updateUserRequest.getRoles()) {
                Role existingRole = IRoleRepository.findByName(role.getName())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + role.getName()));
                updatedRoles.add(existingRole);
            }
            user.setRoles(updatedRoles);
        }
        user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        IUserRepository.save(user);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return new APIResponse<>(userResponse, localizationUtil.getLocalizedMessage(MessageKey.USER_UPDATE_SUCCESS));
    }

}
