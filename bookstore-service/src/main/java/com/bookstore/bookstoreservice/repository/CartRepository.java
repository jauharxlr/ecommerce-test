package com.bookstore.bookstoreservice.repository;

import com.bookstore.bookstoreservice.model.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    List<CartEntity> findAllByUsername(String username);

    Optional<CartEntity> findByUsernameAndIsbn(String username, String isbn);
}
