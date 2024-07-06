package com.ecommerce.apigateway.service;

import com.ecommerce.apigateway.dao.UserDao;
import com.ecommerce.apigateway.dto.UserResponseDto;
import com.ecommerce.securitymodule.service.SecurityUserService;
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
