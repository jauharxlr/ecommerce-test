package com.bookstore.bookstoreservice.model.dto;

import com.bookstore.bookstoreservice.model.entity.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDto {
    private Long id;
    private String name;
    private String description;
    private String author;
    private String type;
    private Double price;
    private String isbn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static BookResponseDto fromEntity(BookEntity bookEntity){
        return BookResponseDto.builder()
                .id(bookEntity.getId())
                .name(bookEntity.getName())
                .author(bookEntity.getAuthor())
                .type(bookEntity.getType().getName())
                .price(bookEntity.getPrice())
                .isbn(bookEntity.getIsbn())
                .description(bookEntity.getDescription())
                .createdAt(bookEntity.getCreatedAt())
                .updatedAt(bookEntity.getUpdatedAt())
                .build();
    }
}
