package com.ecommerce.securitymodule.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtility {
    public String getLoggedInUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
