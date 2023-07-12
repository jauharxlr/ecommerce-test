package com.bookstore.bookstoreservice.service.impl;

import com.bookstore.bookstoreservice.model.dto.AddToCartRequestDto;
import com.bookstore.bookstoreservice.model.dto.CartResponseDto;
import com.bookstore.bookstoreservice.model.dto.CheckoutRequest;
import com.bookstore.bookstoreservice.model.dto.CheckoutResponseDto;
import com.bookstore.bookstoreservice.model.entity.BookEntity;
import com.bookstore.bookstoreservice.model.entity.CartEntity;
import com.bookstore.bookstoreservice.model.entity.TypeEntity;
import com.bookstore.bookstoreservice.repository.CartRepository;
import com.bookstore.bookstoreservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final BookServiceImpl bookService;
    private final TypeServiceImpl typeService;

    @Override
    public void addToCart(String username, AddToCartRequestDto addToCartRequestDto) {
        BookEntity bookEntity = bookService.getByIsbn(addToCartRequestDto.getIsbn());
        Optional<CartEntity> cartEntityOptional = cartRepository.findByUsernameAndIsbn(username, addToCartRequestDto.getIsbn());
        Integer qty = cartEntityOptional.isPresent() ? cartEntityOptional.get().getQuantity() + 1 : 1;
        CartEntity cartEntity = addToCartRequestDto.toEntity(username, bookEntity, qty);
        if (cartEntityOptional.isPresent()){
            cartEntity.setId(cartEntityOptional.get().getId());
        }
        cartRepository.save(cartEntity);
    }

    @Override
    public CartResponseDto getCart(String username) {
        List<CartEntity> cartEntityList = cartRepository.findAllByUsername(username);
        List<CartResponseDto.CartedItems> particulars = cartEntityList.stream()
                .map(entity -> CartResponseDto.CartedItems.fromEntity(entity))
                .collect(Collectors.toList());
        Double totalAmt = cartEntityList.stream()
                .mapToDouble(entity -> entity.getPrice() * entity.getQuantity())
                .reduce(0, (a, b) -> a + b);
        return CartResponseDto.builder()
                .particulars(particulars)
                .totalAmount(totalAmt)
                .build();
    }

    @Override
    public void deleteFromCart(String username, String isbn, Integer quantity) {
        Optional<CartEntity> cartEntityOptional = cartRepository.findByUsernameAndIsbn(username, isbn);
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
    public CheckoutResponseDto checkout(String username, CheckoutRequest checkoutRequest) {
        CartResponseDto cartResponseDto = getCart(username);
        CheckoutResponseDto checkoutResponseDto = CheckoutResponseDto.builder()
                .particulars(cartResponseDto.getParticulars())
                .totalAmount(cartResponseDto.getTotalAmount())
                .discountAmount(0.0)
                .payableAmount(cartResponseDto.getTotalAmount())
                .build();

        if(StringUtils.hasText(checkoutRequest.getPromotionalCode())){
            TypeEntity typeEntity = typeService.getByPromotionalCode(checkoutRequest.getPromotionalCode());
            double sumOfTotalOfEligibleBooks = checkoutResponseDto.getParticulars().stream()
                    .filter(book->book.getType().equals(typeEntity.getName()))
                    .mapToDouble(book-> book.getPrice() * book.getQuantity()).sum();
            double discountAmount = (sumOfTotalOfEligibleBooks * typeEntity.getDiscountPercentage()) / 100;
            checkoutResponseDto.setDiscountAmount(discountAmount);
            checkoutResponseDto.setPayableAmount(checkoutResponseDto.getTotalAmount()-discountAmount);
            checkoutResponseDto.setAppliedPromotionalCode(checkoutRequest.getPromotionalCode());
            checkoutResponseDto.setPromotionAppliedForType(typeEntity.getName());
        }
        clearCart(username);
        return checkoutResponseDto;
    }

    public void clearCart(String username){
        cartRepository.deleteAll(cartRepository.findAllByUsername(username));
    }

}
