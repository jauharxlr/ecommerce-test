package com.ecommerce.productservice.model.dto;

import com.ecommerce.productservice.model.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductResponseDto fromEntity(ProductEntity productEntity){
        return ProductResponseDto.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .description(productEntity.getDescription())
                .createdAt(productEntity.getCreatedAt())
                .updatedAt(productEntity.getUpdatedAt())
                .build();
    }
}
