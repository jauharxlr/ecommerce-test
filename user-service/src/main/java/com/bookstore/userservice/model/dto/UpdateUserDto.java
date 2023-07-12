package com.bookstore.userservice.model.dto;

import com.bookstore.userservice.constant.AppConstants;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
public class UpdateUserDto {
    @NotEmpty(message = AppConstants.ErrorMessage.FULLNAME_NOT_EMPTY)
    private String fullname;
    private String contactNumber;
}
