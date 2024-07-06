package com.ecommerce.userservice.controller;

import com.ecommerce.securitymodule.util.AuthUtility;
import com.ecommerce.userservice.constant.AppConstants;
import com.ecommerce.userservice.constant.UserRole;
import com.ecommerce.userservice.exception.UserRegistrationException;
import com.ecommerce.userservice.model.dto.UpdateUserDto;
import com.ecommerce.userservice.model.dto.UserRequestDto;
import com.ecommerce.userservice.model.dto.UserResponseDto;
import com.ecommerce.userservice.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.Endpoints.USER)
public class UserController {

    private final UserService userService;
    private final AuthUtility authUtility;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Get users list", notes = "Requires JWT authorization")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{emailId}")
    @ApiOperation(value = "Get a user by userId", notes = "Requires JWT authorization")
    public ResponseEntity<UserResponseDto> getUserBy(@PathVariable String emailId){
        String username = authUtility.getLoggedInUsername();
        log.info("Get user emailId={} auth={}", emailId, username);
        Optional<UserResponseDto> userResponseDtoOptional = userService.getUserBy(username);
        if(userResponseDtoOptional.isPresent()){
            UserResponseDto userResponseDto = userService.getUserBy(emailId).get();
            if(username.equals(userResponseDto.getEmailId()) ||
                    userService.getUserBy(username).get().getUserRole().equals(UserRole.ADMIN)){
                return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }

    @PostMapping(AppConstants.Endpoints.REGISTER)
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto userRequestDto) throws UserRegistrationException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userRequestDto));
    }

    @PutMapping("/{userId}")
    @ApiOperation(value = "Update a user by userId", notes = "Requires JWT authorization")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UpdateUserDto updateUserDto){
        String username = authUtility.getLoggedInUsername();
        Optional<UserResponseDto> userResponseDtoOptional = userService.getUserBy(username);
        if(userResponseDtoOptional.isPresent()){
            if(username.equals(userService.getUserBy(userId).getEmailId())){
                return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId, updateUserDto));
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
