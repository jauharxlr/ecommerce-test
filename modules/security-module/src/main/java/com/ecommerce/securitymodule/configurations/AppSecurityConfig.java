package com.ecommerce.securitymodule.configurations;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.security")
public class AppSecurityConfig {
    Boolean isBasicAuthEnabled;
    Boolean isJwtAuthEnabled;
    Boolean addBasicAuthHeader;
    String[] allowedEndpoints;
    @Value("${spring.security.user.username}")
    String username;
    @Value("${spring.security.user.password}")
    String password;

    public String getBasicAuthToken() {
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        return "Basic " + encodedAuth;
    }
}
