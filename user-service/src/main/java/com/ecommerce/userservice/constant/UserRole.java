package com.ecommerce.userservice.constant;

public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");
    private String name;

    private UserRole(String name) {
        this.name = name;
    }
}
