package com.ecommerce.userservice.service;

import com.ecommerce.userservice.exception.UserRegistrationException;
import com.ecommerce.userservice.model.dto.UpdateUserDto;
import com.ecommerce.userservice.model.dto.UserRequestDto;
import com.ecommerce.userservice.model.dto.UserResponseDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserBy(Long userId);
    Optional<UserResponseDto> getUserBy(String emailId);

    UserResponseDto registerUser(UserRequestDto userRequestDto) throws UserRegistrationException;

    UserResponseDto updateUser(Long userId, UpdateUserDto updateUserDto);
}
