package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.model.dto.ProductRequestDto;
import com.ecommerce.productservice.model.dto.ProductResponseDto;
import com.ecommerce.productservice.model.entity.ProductEntity;
import com.ecommerce.productservice.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl bookService;

    @Test
    void addBook_shouldSaveBookEntity() {
        ProductRequestDto productRequestDto = new ProductRequestDto();
        when(productRepository.save(any())).thenReturn(new ProductEntity());
        bookService.addProduct(productRequestDto);
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void updateBook_shouldUpdateExistingBookEntity() {
        Long bookId = 1L;
        ProductRequestDto productRequestDto = new ProductRequestDto();
        ProductEntity existingProductEntity = ProductEntity.builder().price(100.0).name("apple").build();
        existingProductEntity.setId(bookId);
        when(productRepository.findById(any())).thenReturn(Optional.of(existingProductEntity));
        when(productRepository.save(any())).thenReturn(existingProductEntity);
        bookService.updateProduct(bookId, productRequestDto);
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void deleteBook_shouldDeleteExistingBookEntity() {
        ProductEntity existingProductEntity = new ProductEntity();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(existingProductEntity));
        bookService.deleteProduct(1L);
        verify(productRepository, times(1)).delete(existingProductEntity);
    }

    @Test
    void getBook_shouldReturnBookResponseDto() {
        ProductEntity existingProductEntity = ProductEntity.builder().price(100.0).name("apple").build();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(existingProductEntity));
        ProductResponseDto productResponseDto = bookService.getProduct(1L);
        Assertions.assertThat(productResponseDto).isNotNull();
    }

    @Test
    void getAllBooks_shouldReturnListOfBookResponseDto() {
        List<ProductEntity> productEntityList = Arrays.asList(ProductEntity.builder().price(100.0).name("apple").build());
        when(productRepository.findAll()).thenReturn(productEntityList);
        List<ProductResponseDto> productResponseDtoList = bookService.getAllProducts();
        Assertions.assertThat(productResponseDtoList).isNotEmpty();
    }


    @Test
    void getAllBooksByName_shouldReturnListOfBookResponseDto() {
        List<ProductEntity> productEntityList = Arrays.asList(ProductEntity.builder().price(100.0).name("apple").build());
        when(productRepository.findByNameContaining(anyString())).thenReturn(productEntityList);
        List<ProductResponseDto> productResponseDtoList = bookService.getAllProductsByName("book");
        Assertions.assertThat(productResponseDtoList).isNotEmpty();
    }
}