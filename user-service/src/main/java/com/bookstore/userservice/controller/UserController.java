package com.bookstore.userservice.controller;

import com.bookstore.userservice.constant.AppConstants;
import com.bookstore.userservice.constant.UserRole;
import com.bookstore.userservice.exception.UserRegistrationException;
import com.bookstore.userservice.model.dto.UpdateUserDto;
import com.bookstore.userservice.model.dto.UserRequestDto;
import com.bookstore.userservice.model.dto.UserResponseDto;
import com.bookstore.userservice.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    @ApiOperation(value = "Get users list", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @ApiOperation(value = "Get a user by userId", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @GetMapping("/{emailId}")
    public ResponseEntity<UserResponseDto> getUserBy(@ApiIgnore Authentication authentication, @PathVariable String emailId){
        log.info("Get user #{}", emailId, authentication.getName());
        Optional<UserResponseDto> userResponseDtoOptional = userService.getUserBy(authentication.getName());
        if(userResponseDtoOptional.isPresent()){
            UserResponseDto userResponseDto = userService.getUserBy(emailId).get();
            if(authentication.getName().equals(userResponseDto.getEmailId()) ||
                    userService.getUserBy(authentication.getName()).get().getUserRole().equals(UserRole.ADMIN)){
                return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }

    @PostMapping(AppConstants.Endpoints.REGISTER)
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto userRequestDto) throws UserRegistrationException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userRequestDto));
    }

    @ApiOperation(value = "Update a user by userId", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@ApiIgnore Authentication authentication, @PathVariable Long userId, @Valid @RequestBody UpdateUserDto updateUserDto){
        Optional<UserResponseDto> userResponseDtoOptional = userService.getUserBy(authentication.getName());
        if(userResponseDtoOptional.isPresent()){
            if(authentication.getName().equals(userService.getUserBy(userId).getEmailId())){
                return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId, updateUserDto));
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
