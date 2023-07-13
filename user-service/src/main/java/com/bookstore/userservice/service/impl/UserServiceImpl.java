package com.bookstore.userservice.service.impl;

import com.bookstore.userservice.constant.AppConstants;
import com.bookstore.userservice.constant.UserRole;
import com.bookstore.userservice.exception.UserException;
import com.bookstore.userservice.exception.UserRegistrationException;
import com.bookstore.userservice.model.dto.UpdateUserDto;
import com.bookstore.userservice.model.dto.UserRequestDto;
import com.bookstore.userservice.model.dto.UserResponseDto;
import com.bookstore.userservice.model.entity.UserEntity;
import com.bookstore.userservice.repository.UserRepository;
import com.bookstore.userservice.service.UserService;
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

    @PostConstruct
    public void addAdminUser() {
        log.debug("Checking for admin user record.");
        if (CollectionUtils.isEmpty(userRepository.findAllByUserRole(UserRole.ADMIN))) {
            log.debug("Admin record not found! Creating first entry.");
            UserEntity userEntity = UserRequestDto.builder()
                    .fullname("Admin")
                    .password("admin123")
                    .emailId("admin@bookstore.com").build().toEntity();
            userEntity.setUserRole(UserRole.ADMIN);
            userRepository.save(userEntity);
        }
    }

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
