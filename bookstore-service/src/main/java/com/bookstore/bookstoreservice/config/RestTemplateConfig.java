package com.bookstore.bookstoreservice.config;

import com.bookstore.securitymodule.configurations.AppSecurityConfig;
import com.bookstore.securitymodule.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Collections;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {
    private final AppSecurityConfig appSecurityConfig;
    private final JwtUtil jwtUtil;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        if (appSecurityConfig.getAddBasicAuthHeader()) {
            restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
                HttpHeaders headers = request.getHeaders();
                headers.set("Authorization", getBasicToken());
                HttpServletRequest incomingRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                headers.set("username", incomingRequest.getHeader("username"));
               log.info(headers.toSingleValueMap().toString());
                return execution.execute(request, body);
            }));
        }
        return restTemplate;
    }

    private String getBasicToken(){
        String auth = appSecurityConfig.getUsername() + ":" + appSecurityConfig.getPassword();
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        return  "Basic " + encodedAuth;
    }

}