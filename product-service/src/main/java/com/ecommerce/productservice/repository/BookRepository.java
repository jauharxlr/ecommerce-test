package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByNameContaining(String name);

}
