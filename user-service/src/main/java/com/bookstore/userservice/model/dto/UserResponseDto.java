package com.bookstore.userservice.model.dto;

import com.bookstore.userservice.constant.UserRole;
import com.bookstore.userservice.model.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private Long userId;
    private String fullname;
    private String contactNumber;
    private String emailId;
    private String password;
    private UserRole userRole;

    public static UserResponseDto toDto(UserEntity userEntity){
        return UserResponseDto.builder()
                .userId(userEntity.getId())
                .fullname(userEntity.getFullname())
                .contactNumber(userEntity.getContactNumber())
                .emailId(userEntity.getEmailId())
                .userRole(userEntity.getUserRole())
                .password(userEntity.getPassword())
                .build();
    }
}
