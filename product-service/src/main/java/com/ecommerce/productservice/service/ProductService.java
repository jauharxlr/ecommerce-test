package com.ecommerce.productservice.service;

import com.ecommerce.productservice.model.dto.ProductRequestDto;
import com.ecommerce.productservice.model.dto.ProductResponseDto;
import com.ecommerce.productservice.model.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    ProductResponseDto addProduct(ProductRequestDto productRequestDto);
    ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto);
    void deleteProduct(Long id);
    ProductResponseDto getProduct(Long id);
    List<ProductResponseDto> getAllProducts();
    List<ProductResponseDto> getAllProductsByName(String name);
    ProductEntity findById(Long id);
}
