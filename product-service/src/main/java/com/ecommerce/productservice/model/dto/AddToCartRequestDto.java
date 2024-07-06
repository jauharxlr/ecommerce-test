package com.ecommerce.productservice.model.dto;

import com.ecommerce.productservice.constant.AppConstants;
import com.ecommerce.productservice.model.entity.CartEntity;
import com.ecommerce.productservice.model.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartRequestDto {
    @NotNull(message = AppConstants.ErrorMessage.ID_NOT_EMPTY)
    private Long id;

    public CartEntity toEntity(String username, ProductEntity productEntity, Integer qty){
        return CartEntity.builder()
                .price(productEntity.getPrice())
                .productName(productEntity.getName())
                .quantity(qty)
                .username(username)
                .build();
    }
}
