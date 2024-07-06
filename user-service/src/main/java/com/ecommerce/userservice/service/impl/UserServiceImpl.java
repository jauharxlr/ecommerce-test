package com.ecommerce.userservice.service.impl;

import com.ecommerce.userservice.constant.AppConstants;
import com.ecommerce.userservice.constant.UserRole;
import com.ecommerce.userservice.exception.UserException;
import com.ecommerce.userservice.exception.UserRegistrationException;
import com.ecommerce.userservice.model.dto.UpdateUserDto;
import com.ecommerce.userservice.model.dto.UserRequestDto;
import com.ecommerce.userservice.model.dto.UserResponseDto;
import com.ecommerce.userservice.model.entity.UserEntity;
import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(entity -> UserResponseDto.toDto(entity)).collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getUserBy(Long userId) {
        return UserResponseDto.toDto(getUser(userId));
    }

    @Override
    public Optional<UserResponseDto> getUserBy(String emailId) {
        log.debug(emailId);
        return Optional.ofNullable(UserResponseDto.toDto(userRepository.findByEmailId(emailId).orElseThrow(() ->
                new UserException(AppConstants.ErrorMessage.USER_NOT_FOUND))));
    }

    @Override
    public UserResponseDto registerUser(UserRequestDto userRequestDto) throws UserRegistrationException {
        try {
            return UserResponseDto.toDto(userRepository.save(userRequestDto.toEntity()));
        } catch (Exception err) {
            throw new UserException(AppConstants.ErrorMessage.EMAIL_ALREADY_EXISTS);
        }
    }

    @Override
    public UserResponseDto updateUser(Long userId, UpdateUserDto updateUserDto) {
        UserEntity userEntity = getUser(userId);
        userEntity.setFullname(updateUserDto.getFullname());
        userEntity.setContactNumber(updateUserDto.getContactNumber());
        return UserResponseDto.toDto(userRepository.save(userEntity));
    }

    private UserEntity getUser(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new UserException(AppConstants.ErrorMessage.USER_NOT_FOUND));
    }
}
