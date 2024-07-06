package com.ecommerce.productservice.model.dto;

import com.ecommerce.productservice.model.entity.CartEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {

    private List<CartedItems> particulars;
    private Double totalAmount;

    @Data
    @Builder
    public static class CartedItems {
        private String productName;
        private Double price;
        private Integer quantity;
        public static CartedItems fromEntity(CartEntity cartEntity){
            return CartedItems.builder()
                    .productName(cartEntity.getProductName())
                    .price(cartEntity.getPrice())
                    .quantity(cartEntity.getQuantity())
                    .build();
        }
    }
}
