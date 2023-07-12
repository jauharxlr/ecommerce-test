package com.bookstore.bookstoreservice.repository;

import com.bookstore.bookstoreservice.model.entity.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<TypeEntity, Long> {

    Optional<TypeEntity> findByName(String name);
    Optional<TypeEntity> findByNameOrPromotionalCode(String name, String promotionalCode);
    Optional<TypeEntity> findByPromotionalCode(String promotionalCode);

}
