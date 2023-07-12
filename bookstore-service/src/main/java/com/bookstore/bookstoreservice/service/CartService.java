package com.bookstore.bookstoreservice.service;

import com.bookstore.bookstoreservice.model.dto.AddToCartRequestDto;
import com.bookstore.bookstoreservice.model.dto.CartResponseDto;
import com.bookstore.bookstoreservice.model.dto.CheckoutRequest;
import com.bookstore.bookstoreservice.model.dto.CheckoutResponseDto;

public interface CartService {
    void addToCart(String username, AddToCartRequestDto addToCartRequestDto);
    CartResponseDto getCart(String username);
    void deleteFromCart(String username, String isbn, Integer quantity);
    CheckoutResponseDto checkout(String username, CheckoutRequest checkoutRequest);
    void clearCart(String username);
}
