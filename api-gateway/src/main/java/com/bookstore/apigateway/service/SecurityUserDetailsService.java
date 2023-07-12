package com.bookstore.apigateway.service;

import com.bookstore.apigateway.dao.UserDao;
import com.bookstore.apigateway.dto.UserResponseDto;
import com.bookstore.securitymodule.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements SecurityUserService {
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
