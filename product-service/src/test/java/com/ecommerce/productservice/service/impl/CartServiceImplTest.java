package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.model.dto.AddToCartRequestDto;
import com.ecommerce.productservice.model.dto.CartResponseDto;
import com.ecommerce.productservice.model.dto.CheckoutResponseDto;
import com.ecommerce.productservice.model.entity.CartEntity;
import com.ecommerce.productservice.model.entity.ProductEntity;
import com.ecommerce.productservice.repository.CartRepository;
import com.ecommerce.productservice.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    void addToCart_shouldSaveCartEntity() {
        AddToCartRequestDto addToCartRequestDto = AddToCartRequestDto.builder().id(10L).build();
        when(productService.findById(any())).thenReturn(new ProductEntity());
        cartService.addToCart("username", addToCartRequestDto);
        verify(cartRepository, times(1)).save(any());
    }

    @Test
    void getCart_shouldReturnCartResponseDto() {
        CartEntity cartEntity = CartEntity.builder().price(10.0).quantity(1).build();
        List<CartEntity> cartEntityList = Arrays.asList(cartEntity);
        when(cartRepository.findAllByUsername(anyString())).thenReturn(cartEntityList);
        CartResponseDto cartResponseDto = cartService.getCart("username");
        Assertions.assertThat(cartResponseDto).isNotNull();
    }

    @Test
    void deleteFromCart_shouldDeleteCartEntity() {
        CartEntity cartEntity = CartEntity.builder().price(10.0).quantity(1).build();
        Optional<CartEntity> cartEntityOptional = Optional.of(cartEntity);
        when(cartRepository.findByUsernameAndId(any(), any())).thenReturn(cartEntityOptional);
        cartService.deleteFromCart(any(), any(), 1);
        verify(cartRepository, times(1)).delete(any());
    }

    @Test
    void checkout_shouldReturnCheckoutResponseDto() {
        CartEntity cartEntity = CartEntity.builder().price(10.0).quantity(1).build();
        List<CartEntity> cartEntityList = Collections.singletonList(cartEntity);
        when(cartRepository.findAllByUsername(any())).thenReturn(cartEntityList);
        List<CartResponseDto.CartedItems> cartedItems = Arrays.asList(CartResponseDto.CartedItems.fromEntity(cartEntity));
        CartResponseDto cartResponseDto = CartResponseDto.builder().totalAmount(10.0).particulars(cartedItems).build();
        CheckoutResponseDto checkoutResponseDto = cartService.checkout("username");
        Assertions.assertThat(checkoutResponseDto).isNotNull();
    }

    @Test
    void clearCart_shouldDeleteAllCartEntities() {
        List<CartEntity> cartEntityList = Collections.singletonList(new CartEntity());
        when(cartRepository.findAllByUsername(anyString())).thenReturn(cartEntityList);

        cartService.clearCart("username");

        verify(cartRepository, times(1)).deleteAll(any());
    }
}