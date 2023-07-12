package com.bookstore.bookstoreservice.model.dto;

import com.bookstore.bookstoreservice.model.entity.TypeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeResponseDto {
    Long id;
    String name;
    String promotionalCode;
    Double discountPercentage = 0.0;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    public static TypeResponseDto toDto(TypeEntity typeEntity) {
        return TypeResponseDto.builder()
                .id(typeEntity.getId())
                .name(typeEntity.getName())
                .promotionalCode(typeEntity.getPromotionalCode())
                .discountPercentage(typeEntity.getDiscountPercentage())
                .createdAt(typeEntity.getCreatedAt())
                .updatedAt(typeEntity.getUpdatedAt())
                .build();
    }
}
