package com.ecommerce.userservice.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthRequestDto {
    @NotEmpty(message = "Email Id should not be empty!")
    String emailId;
    @NotEmpty(message = "Password should not be empty!")
    String password;
}
