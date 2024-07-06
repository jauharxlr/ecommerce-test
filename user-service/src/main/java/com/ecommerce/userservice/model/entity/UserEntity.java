package com.ecommerce.userservice.model.entity;

import com.ecommerce.userservice.constant.AppConstants;
import com.ecommerce.userservice.constant.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = AppConstants.DB.USER_TABLE)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullname;
    private String password;
    private String contactNumber;

    @Column(unique = true)
    private String emailId;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
