package com.ecommerce.productservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutResponseDto {
    List<CartResponseDto.CartedItems> particulars;
    Double totalAmount;
    Double payableAmount;
}
