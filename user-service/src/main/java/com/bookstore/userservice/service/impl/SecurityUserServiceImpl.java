package com.bookstore.userservice.service.impl;

import com.bookstore.userservice.constant.AppConstants;
import com.bookstore.userservice.exception.UserException;
import com.bookstore.userservice.model.entity.UserEntity;
import com.bookstore.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserServiceImpl implements com.bookstore.securitymodule.service.SecurityUserService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmailId(username).orElseThrow(()-> new UserException(AppConstants.ErrorMessage.USER_NOT_FOUND));
        return User.withUsername(userEntity.getEmailId())
                .password(userEntity.getPassword())
                .roles(userEntity.getUserRole().name())
                .build();
    }
}
