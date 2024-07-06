package com.ecommerce.productservice.config;

import com.ecommerce.securitymodule.configurations.AppSecurityConfig;
import com.ecommerce.securitymodule.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
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
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        if (appSecurityConfig.getAddBasicAuthHeader()) {
            restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
                HttpHeaders headers = request.getHeaders();
                headers.set("Authorization", getBasicToken());
                HttpServletRequest incomingRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                String username = incomingRequest.getHeader("username");
                if(StringUtils.isEmpty(username) && !StringUtils.isEmpty(incomingRequest.getHeader("Authorization"))){
                    username = jwtUtil.extractUsernameFromRequest(incomingRequest);
                }
                headers.set("username", username);
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