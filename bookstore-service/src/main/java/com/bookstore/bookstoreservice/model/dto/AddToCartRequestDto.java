package com.bookstore.bookstoreservice.model.dto;

import com.bookstore.bookstoreservice.constant.AppConstants;
import com.bookstore.bookstoreservice.model.entity.BookEntity;
import com.bookstore.bookstoreservice.model.entity.CartEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartRequestDto {
    @NotEmpty(message = AppConstants.ErrorMessage.ISBN_NOT_EMPTY)
    private String isbn;

    public CartEntity toEntity(String username, BookEntity bookEntity, Integer qty){
        return CartEntity.builder()
                .price(bookEntity.getPrice())
                .bookName(bookEntity.getName())
                .quantity(qty)
                .type(bookEntity.getType().getName())
                .isbn(bookEntity.getIsbn())
                .username(username)
                .build();
    }
}
