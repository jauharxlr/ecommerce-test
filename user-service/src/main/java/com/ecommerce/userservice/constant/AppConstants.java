package com.ecommerce.userservice.constant;

public interface AppConstants {
    interface Endpoints {
        String USER = "/user";
        String AUTHENTICATE = "/authenticate";
        String REGISTER = "/register";
    }

    interface DB {
        String PREFIX = "bks_";
        String USER_TABLE = PREFIX + "user";
    }

    interface ErrorMessage {
        String USER_NOT_FOUND = "User not found!";
        String EMAIL_ALREADY_EXISTS = "Email ID already registered!";
        String FULLNAME_NOT_EMPTY = "Fullname cannot be empty!";
        String PASSWORD_NOT_EMPTY = "Password should not be empty!";
        String EMAIL_ID_NOT_EMPTY = "Email ID should not be empty!";
    }
}
