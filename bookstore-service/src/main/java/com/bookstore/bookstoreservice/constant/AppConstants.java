package com.bookstore.bookstoreservice.constant;

public interface AppConstants {
    interface Endpoints {
        String STORE = "/store";
        String BOOKS = "/books";
        String CART = "/cart";
        String TYPE = "/type";
        String CHECKOUT = "/checkout";
    }

    interface DB {
        String PREFIX = "bks_";
        String BOOKS_TABLE = PREFIX + "books";
        String CART_TABLE = PREFIX + "cart";
        String TYPE_TABLE = PREFIX + "type";
    }

    interface ErrorMessage {
        String BOOK_NOT_FOUND = "Book not found!";
        String TYPE_NOT_FOUND = "Type not found!";
        String BOOK_NAME_NOT_EMPTY = "Book name cannot be empty!";
        String ISBN_NOT_EMPTY = "ISBN cannot be empty!";
        String TYPE_NOT_EMPTY = "Type cannot be empty!";
        String ISBN_ALREADY_PRESENT = "Duplicate ISBN! This ISBN already present.";
        String NAME_AND_PROMO_BE_UNIQUE = "Name and promotional code should be unique!";
        String PROMO_NOT_FOUND = "Promotional code not found!";
    }
}
