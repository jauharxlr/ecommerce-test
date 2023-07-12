package com.bookstore.userservice.service;

import com.bookstore.userservice.exception.UserException;
import com.bookstore.userservice.exception.UserRegistrationException;
import com.bookstore.userservice.model.dto.UpdateUserDto;
import com.bookstore.userservice.model.dto.UserRequestDto;
import com.bookstore.userservice.model.dto.UserResponseDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserBy(Long userId);
    Optional<UserResponseDto> getUserBy(String emailId);

    UserResponseDto registerUser(UserRequestDto userRequestDto) throws UserRegistrationException;

    UserResponseDto updateUser(Long userId, UpdateUserDto updateUserDto);
}
