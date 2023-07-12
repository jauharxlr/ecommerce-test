package com.bookstore.bookstoreservice.model.dto;

import com.bookstore.bookstoreservice.model.entity.CartEntity;
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
        private String isbn;
        private String bookName;
        private Double price;
        private Integer quantity;
        private String type;
        public static CartedItems fromEntity(CartEntity cartEntity){
            return CartedItems.builder()
                    .bookName(cartEntity.getBookName())
                    .price(cartEntity.getPrice())
                    .quantity(cartEntity.getQuantity())
                    .isbn(cartEntity.getIsbn())
                    .type(cartEntity.getType())
                    .build();
        }
    }
}
