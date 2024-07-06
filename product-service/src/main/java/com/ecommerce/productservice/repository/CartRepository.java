package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.model.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    List<CartEntity> findAllByUsername(String username);

    Optional<CartEntity> findByUsernameAndId(String username, Long id);
}
