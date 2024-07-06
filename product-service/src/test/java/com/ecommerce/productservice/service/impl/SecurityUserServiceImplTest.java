package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.dao.UserDao;
import com.ecommerce.productservice.model.dto.UserResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityUserServiceImplTest {

    @Mock
    private UserDao mockUserDao;

    @InjectMocks
    private SecurityUserServiceImpl securityUserServiceImplUnderTest;


    @Test
    void testLoadUserByUsername() {

        final UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUserId(0L);
        userResponseDto.setFullname("fullname");
        userResponseDto.setContactNumber("contactNumber");
        userResponseDto.setUserRole("userRole");
        userResponseDto.setPassword("password");
        when(mockUserDao.getUserDetails("username")).thenReturn(userResponseDto);

        final UserDetails result = securityUserServiceImplUnderTest.loadUserByUsername("username");

        Assertions.assertThat(result).isNotNull();
    }
}
