package com.balenciaga.Services.User;

//import com.balenciaga.Config.Validate;
import com.balenciaga.DTO.Request.User.*;
import com.balenciaga.DTO.Response.PagingResponse;
import com.balenciaga.Exceptions.DataExistedException;
import com.balenciaga.Repositories.IRoleRepository;
import com.balenciaga.DTO.Response.APIResponse;
import com.balenciaga.DTO.Response.User.UserResponse;
import com.balenciaga.Constants.MessageKey;
import com.balenciaga.Entities.Role;
import com.balenciaga.Entities.User;
import com.balenciaga.Repositories.IUserRepository;
import com.balenciaga.Repositories.Specification.UserSpecification;
import com.balenciaga.Utils.LocalizationUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


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
    private PasswordEncoder passwordEncode;
    @Autowired
    private IRoleRepository IRoleRepository;


    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public PagingResponse<List<UserResponse>> getUser(@ModelAttribute UserRequest userRequest) {
        ArrayList<UserResponse> userResponseList;
        List<User> userList;
        Pageable pageable;
        if(userRequest.getPage() == 0 && userRequest.getLimit() == 0){
            Specification<User> specification = UserSpecification.filterUsers(userRequest.getRole(),userRequest.getEmployeeCode(), userRequest.getFullName(), userRequest.getEmail());
            userList = IUserRepository.findAll(specification);
            userResponseList = new ArrayList<>(userList.stream()
                    .map(user -> modelMapper.map(user, UserResponse.class))
                    .toList());
            List<String> messages = new ArrayList<>();
            messages.add(localizationUtil.getLocalizedMessage(MessageKey.USER_GET_SUCCESS));
            return new PagingResponse<>(userResponseList, messages, 1, (long) userResponseList.size());
        }else{
            userRequest.setPage(Math.max(userRequest.getPage(), 1));
            pageable = PageRequest.of(userRequest.getPage() - 1, userRequest.getLimit());
        }
        Specification<User> specification = UserSpecification.filterUsers(userRequest.getRole(),userRequest.getEmployeeCode(), userRequest.getFullName(), userRequest.getEmail());
        Page<User> userPage = IUserRepository.findAll(specification, pageable);
        userList = userPage.getContent();
        userResponseList = new ArrayList<>(userList.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList());
        return new PagingResponse<>(userResponseList, List.of(localizationUtil.getLocalizedMessage(MessageKey.USER_GET_SUCCESS)), userPage.getTotalPages(), userPage.getTotalElements());
    }

    @Override
    public APIResponse<User> createUser(CreateUserRequest createUserRequest) {
        if(IUserRepository.existsByEmail(createUserRequest.getEmail())) {
            List<String> messages = new ArrayList<>();
            messages.add(localizationUtil.getLocalizedMessage(MessageKey.USER_ALREADY_EXIST));
            logger.info("User already existed");
            return new APIResponse<>(null,messages);
        }
        if(IUserRepository.existsByPhoneNumber(createUserRequest.getPhoneNumber())){
            List<String> messages = new ArrayList<>();
            messages.add(localizationUtil.getLocalizedMessage(MessageKey.USER_PHONE_EXISTED));
            logger.info("Phone number already existed");
            return new APIResponse<>(null,messages);
        }
        User userCreated = modelMapper.map(createUserRequest, User.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            userCreated.setBirthday(dateFormat.parse(createUserRequest.getBirthday()));
        } catch (ParseException e) {
            logger.info("Invalid date format. Expected format: yyyy-MM-dd");
            throw new RuntimeException("Invalid date format. Expected format: yyyy-MM-dd");
        }
        userCreated.setPassword(passwordEncode.encode(createUserRequest.getPassword()));
        userCreated.setFullName(createUserRequest.getFirstName() + " "+createUserRequest.getMiddleName()+ " " + createUserRequest.getLastName());
        if (createUserRequest.getRoles() != null) {
            Set<Role> managedRoles = new HashSet<>();
            for (Role role : createUserRequest.getRoles()) {
                Role existingRole = IRoleRepository.findByName(role.getName())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + role.getName()));
                managedRoles.add(existingRole);
            }
            userCreated.setRoles(managedRoles);
        }
        List<String> messages = new ArrayList<>();
        messages.add(localizationUtil.getLocalizedMessage(MessageKey.USER_CREATE_SUCCESS));
        IUserRepository.save(userCreated);
        logger.info("User created successfully");
        return new APIResponse<>(userCreated, messages);
    }

    @Override
    public APIResponse<UserResponse> getOneUser(String userID) {
        User user = IUserRepository.findById(UUID.fromString(userID))
                .orElseThrow(() -> new DataExistedException("User not found with ID" + userID));
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        List<String> messages = new ArrayList<>();
        messages.add(localizationUtil.getLocalizedMessage(MessageKey.USER_GET_ONE_SUCCESS));
        logger.info("Get user by ID successfully");
        return new APIResponse<>(userResponse, messages);
    }

    @Override
    public APIResponse<UserResponse> updateUser(UpdateUserRequest updateUserRequest) {
        User user = IUserRepository.findByEmail(updateUserRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + updateUserRequest.getEmail()));
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
        user.setPassword(passwordEncode.encode(updateUserRequest.getPassword()));
        user.setFullName(updateUserRequest.getFirstName() + " "+updateUserRequest.getMiddleName()+ " " + updateUserRequest.getLastName());
        IUserRepository.save(user);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        List<String> messages = new ArrayList<>();
        messages.add(localizationUtil.getLocalizedMessage(MessageKey.USER_UPDATE_SUCCESS));
        logger.info("Update user successfully");
        return new APIResponse<>(userResponse, messages);
    }

    @Override
    public APIResponse<Boolean> deleteUser(String userID) {
        User user = IUserRepository.findById(UUID.fromString(userID))
                .orElseThrow(() -> new DataExistedException("User not found with ID" + userID));
        user.getRoles().clear();
        IUserRepository.save(user);
        IUserRepository.delete(user);
        List<String> messages = new ArrayList<>();
        messages.add(localizationUtil.getLocalizedMessage(MessageKey.USER_DELETE_SUCCESS));
        logger.info("Delete user successfully");
        return new APIResponse<>(true, messages);
    }

    @Override
    public APIResponse<Boolean> deleteMutiUser(UserMutiDeleteRequest userMutiDeleteRequest) {
        List<String> userList = userMutiDeleteRequest.getId();
        userList.forEach(userID -> {
            User userDelete = IUserRepository.findById(UUID.fromString(userID))
                    .orElseThrow(() -> new DataExistedException("User not found with ID" + userID));
            userDelete.getRoles().clear();
            IUserRepository.save(userDelete);
            IUserRepository.delete(userDelete);
        });
        List<String> messages = new ArrayList<>();
        messages.add(localizationUtil.getLocalizedMessage(MessageKey.USER_DELETE_SUCCESS));
        logger.info("Delete user successfully");
        return new APIResponse<>(true, messages);
    }


}
