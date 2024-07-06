package com.ecommerce.userservice.service.impl;

import com.ecommerce.securitymodule.service.SecurityUserService;
import com.ecommerce.userservice.constant.AppConstants;
import com.ecommerce.userservice.exception.UserException;
import com.ecommerce.userservice.model.entity.UserEntity;
import com.ecommerce.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserServiceImpl implements SecurityUserService {
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
