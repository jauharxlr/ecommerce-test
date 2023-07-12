package com.bookstore.userservice.repository;

import com.bookstore.userservice.constant.UserRole;
import com.bookstore.userservice.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailId(String emailId);
    List<UserEntity> findAllByUserRole(UserRole userRole);
}
