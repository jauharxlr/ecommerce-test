package com.ecommerce.productservice.exception;

public class BookException extends RuntimeException {
    public BookException() {
        super();
    }
    public BookException(String message) {
        super(message);
    }
}
