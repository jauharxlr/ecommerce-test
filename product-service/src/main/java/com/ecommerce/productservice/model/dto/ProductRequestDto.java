package com.ecommerce.productservice.model.dto;

import com.ecommerce.productservice.constant.AppConstants;
import com.ecommerce.productservice.model.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    @NotEmpty(message = AppConstants.ErrorMessage.PRODUCT_NAME_NOT_EMPTY)
    private String name;
    private String description;
    @NotNull
    private Double price;

    public ProductEntity toEntity(){
        return ProductEntity.builder()
                .name(name)
                .description(description)
                .price(price)
                .build();
    }
}
