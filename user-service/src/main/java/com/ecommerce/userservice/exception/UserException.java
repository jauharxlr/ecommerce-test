package com.ecommerce.userservice.exception;

public class UserException extends RuntimeException{
    public UserException() {
        super();
    }
    public UserException(String message) {
        super(message);
    }
}
