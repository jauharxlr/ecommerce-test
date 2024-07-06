package com.ecommerce.productservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String fullname;
    private String contactNumber;
    private String emailId;
    private String userRole;
    private String password;
}
