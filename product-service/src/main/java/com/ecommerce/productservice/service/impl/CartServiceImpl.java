package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.repository.CartRepository;
import com.ecommerce.productservice.model.dto.AddToCartRequestDto;
import com.ecommerce.productservice.model.dto.CartResponseDto;
import com.ecommerce.productservice.model.dto.CheckoutResponseDto;
import com.ecommerce.productservice.model.entity.ProductEntity;
import com.ecommerce.productservice.model.entity.CartEntity;
import com.ecommerce.productservice.service.CartService;
import com.ecommerce.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;

    @Override
    public void addToCart(String username, AddToCartRequestDto addToCartRequestDto) {
        ProductEntity productEntity = productService.findById(addToCartRequestDto.getId());
        Optional<CartEntity> cartEntityOptional = cartRepository.findByUsernameAndId(username, addToCartRequestDto.getId());
        Integer qty = cartEntityOptional.map(entity -> entity.getQuantity() + 1).orElse(1);
        CartEntity cartEntity = addToCartRequestDto.toEntity(username, productEntity, qty);
        cartEntityOptional.ifPresent(entity -> cartEntity.setId(entity.getId()));
        cartRepository.save(cartEntity);
    }

    @Override
    public CartResponseDto getCart(String username) {
        List<CartEntity> cartEntityList = cartRepository.findAllByUsername(username);
        List<CartResponseDto.CartedItems> particulars = cartEntityList.stream()
                .map(CartResponseDto.CartedItems::fromEntity)
                .collect(Collectors.toList());
        Double totalAmt = cartEntityList.stream()
                .mapToDouble(entity -> entity.getPrice() * entity.getQuantity())
                .reduce(0, Double::sum);
        return CartResponseDto.builder()
                .particulars(particulars)
                .totalAmount(totalAmt)
                .build();
    }

    @Override
    public void deleteFromCart(String username, Long id, Integer quantity) {
        Optional<CartEntity> cartEntityOptional = cartRepository.findByUsernameAndId(username, id);
        if (cartEntityOptional.isPresent()) {
            CartEntity cartEntity = cartEntityOptional.get();
            if (cartEntity.getQuantity() < quantity || cartEntity.getQuantity() - quantity==0) {
                cartRepository.delete(cartEntity);
            } else {
                cartEntity.setQuantity(cartEntity.getQuantity() - quantity);
                cartRepository.save(cartEntity);
            }
        }
    }

    @Override
    public CheckoutResponseDto checkout(String username) {
        CartResponseDto cartResponseDto = getCart(username);
        CheckoutResponseDto checkoutResponseDto = CheckoutResponseDto.builder()
                .particulars(cartResponseDto.getParticulars())
                .totalAmount(cartResponseDto.getTotalAmount())
                .payableAmount(cartResponseDto.getTotalAmount())
                .build();
        clearCart(username);
        return checkoutResponseDto;
    }

    public void clearCart(String username){
        cartRepository.deleteAll(cartRepository.findAllByUsername(username));
    }

}
