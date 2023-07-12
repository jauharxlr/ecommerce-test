package com.bookstore.bookstoreservice.model.dto;

import com.bookstore.bookstoreservice.constant.AppConstants;
import com.bookstore.bookstoreservice.model.entity.BookEntity;
import com.bookstore.bookstoreservice.model.entity.TypeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDto {

    @NotEmpty(message = AppConstants.ErrorMessage.BOOK_NAME_NOT_EMPTY)
    private String name;
    private String description;
    private String author;

    @NotEmpty(message = AppConstants.ErrorMessage.TYPE_NOT_EMPTY)
    private String type;
    private Double price = 0.0;
    @NotEmpty(message = AppConstants.ErrorMessage.ISBN_NOT_EMPTY)
    private String isbn;

    public BookEntity toEntity(TypeEntity type){
        return BookEntity.builder()
                .name(name)
                .description(description)
                .author(author)
                .type(type)
                .price(price)
                .isbn(isbn)
                .build();
    }
}
