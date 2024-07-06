package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.repository.BookRepository;
import com.ecommerce.productservice.constant.AppConstants;
import com.ecommerce.productservice.exception.BookException;
import com.ecommerce.productservice.model.dto.ProductRequestDto;
import com.ecommerce.productservice.model.dto.ProductResponseDto;
import com.ecommerce.productservice.model.entity.ProductEntity;
import com.ecommerce.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final BookRepository bookRepository;

    @Override
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) {
        ProductEntity productEntity = productRequestDto.toEntity();
        bookRepository.save(productEntity);
        return ProductResponseDto.fromEntity(productEntity);
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) {
        ProductEntity productEntity = get(id);
        productEntity.setId(id);
        productEntity.setName(productRequestDto.getName());
        productEntity.setUpdatedAt(LocalDateTime.now());
        productEntity.setDescription(productRequestDto.getDescription());
        productEntity.setPrice(productRequestDto.getPrice());
        bookRepository.save(productEntity);
        return ProductResponseDto.fromEntity(productEntity);
    }

    @Override
    public void deleteProduct(Long id) {
        bookRepository.delete(get(id));
    }

    @Override
    public ProductResponseDto getProduct(Long id) {
        return ProductResponseDto.fromEntity(get(id));
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return bookRepository.findAll().stream()
                .map(ProductResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> getAllProductsByName(String name) {
        return bookRepository.findByNameContaining(name).stream()
                .map(ProductResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    private ProductEntity get(Long id){
        return bookRepository.findById(id).orElseThrow(()->
                new BookException(AppConstants.ErrorMessage.PRODUCT_NOT_FOUND));
    }

    @Override
    public ProductEntity findById(Long id) {
        return get(id);
    }
}
