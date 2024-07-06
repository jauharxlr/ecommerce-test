package com.ecommerce.productservice.service;

import com.ecommerce.productservice.model.dto.AddToCartRequestDto;
import com.ecommerce.productservice.model.dto.CartResponseDto;
import com.ecommerce.productservice.model.dto.CheckoutResponseDto;

public interface CartService {
    void addToCart(String username, AddToCartRequestDto addToCartRequestDto);
    CartResponseDto getCart(String username);
    void deleteFromCart(String username, Long id, Integer quantity);
    CheckoutResponseDto checkout(String username);
    void clearCart(String username);
}
