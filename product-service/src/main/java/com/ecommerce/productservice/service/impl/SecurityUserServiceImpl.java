package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.dao.UserDao;
import com.ecommerce.productservice.model.dto.UserResponseDto;
import com.ecommerce.securitymodule.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserServiceImpl implements SecurityUserService {
    private final UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserResponseDto userResponseDto = userDao.getUserDetails(username);
        return User.withUsername(username)
                .password(userResponseDto.getPassword())
                .roles(userResponseDto.getUserRole())
                .build();
    }
}
