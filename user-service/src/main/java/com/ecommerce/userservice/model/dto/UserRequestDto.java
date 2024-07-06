package com.ecommerce.userservice.model.dto;

import com.ecommerce.userservice.constant.AppConstants;
import com.ecommerce.userservice.constant.UserRole;
import com.ecommerce.userservice.model.entity.UserEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
public class UserRequestDto {
    @NotEmpty(message = AppConstants.ErrorMessage.FULLNAME_NOT_EMPTY)
    private String fullname;
    @NotEmpty(message = AppConstants.ErrorMessage.PASSWORD_NOT_EMPTY)
    private String password;
    private String contactNumber;
    @NotEmpty(message = AppConstants.ErrorMessage.EMAIL_ID_NOT_EMPTY)
    private String emailId;

    public UserEntity toEntity(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return UserEntity.builder()
                .fullname(fullname)
                .password(bCryptPasswordEncoder.encode(password))
                .contactNumber(contactNumber)
                .emailId(emailId)
                .userRole(UserRole.USER)
                .build();
    }
}
