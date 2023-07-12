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
public class TypeRequestDto {
    String name;
    String promotionalCode;
    Double discountPercentage = 0.0;


    public TypeEntity toEntity(){
        return TypeEntity.builder()
                .name(name)
                .promotionalCode(promotionalCode)
                .discountPercentage(discountPercentage)

                .build();
    }
}
