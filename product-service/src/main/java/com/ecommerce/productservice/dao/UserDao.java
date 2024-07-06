package com.ecommerce.productservice.dao;

import com.ecommerce.productservice.model.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final RestTemplate restTemplate;

    @Value("${endpoints.getuserdetails}")
    String userDetailsEndPoint;

    public UserResponseDto getUserDetails(String username){
        ResponseEntity<UserResponseDto> response = restTemplate.exchange(
                userDetailsEndPoint+username,
                HttpMethod.GET,
                null,
                UserResponseDto.class
        );
        return response.getBody();
    }
}
