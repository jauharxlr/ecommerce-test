package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.model.dto.ProductRequestDto;
import com.ecommerce.productservice.model.dto.ProductResponseDto;
import com.ecommerce.productservice.model.entity.ProductEntity;
import com.ecommerce.productservice.repository.BookRepository;
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
    private BookRepository bookRepository;

    @InjectMocks
    private ProductServiceImpl bookService;

    @Test
    void addBook_shouldSaveBookEntity() {
        ProductRequestDto productRequestDto = new ProductRequestDto();
        when(bookRepository.save(any())).thenReturn(new ProductEntity());
        bookService.addProduct(productRequestDto);
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void updateBook_shouldUpdateExistingBookEntity() {
        Long bookId = 1L;
        ProductRequestDto productRequestDto = new ProductRequestDto();
        ProductEntity existingProductEntity = ProductEntity.builder().price(100.0).name("apple").build();
        existingProductEntity.setId(bookId);
        when(bookRepository.save(any())).thenReturn(existingProductEntity);
        bookService.updateProduct(bookId, productRequestDto);
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void deleteBook_shouldDeleteExistingBookEntity() {
        ProductEntity existingProductEntity = new ProductEntity();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(existingProductEntity));
        bookService.deleteProduct(1L);
        verify(bookRepository, times(1)).delete(existingProductEntity);
    }

    @Test
    void getBook_shouldReturnBookResponseDto() {
        ProductEntity existingProductEntity = ProductEntity.builder().price(100.0).name("apple").build();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(existingProductEntity));
        ProductResponseDto productResponseDto = bookService.getProduct(1L);
        Assertions.assertThat(productResponseDto).isNotNull();
    }

    @Test
    void getAllBooks_shouldReturnListOfBookResponseDto() {
        List<ProductEntity> productEntityList = Arrays.asList(ProductEntity.builder().price(100.0).name("apple").build());
        when(bookRepository.findAll()).thenReturn(productEntityList);
        List<ProductResponseDto> productResponseDtoList = bookService.getAllProducts();
        Assertions.assertThat(productResponseDtoList).isNotEmpty();
    }


    @Test
    void getAllBooksByName_shouldReturnListOfBookResponseDto() {
        List<ProductEntity> productEntityList = Arrays.asList(ProductEntity.builder().price(100.0).name("apple").build());
        when(bookRepository.findByNameContaining(anyString())).thenReturn(productEntityList);
        List<ProductResponseDto> productResponseDtoList = bookService.getAllProductsByName("book");
        Assertions.assertThat(productResponseDtoList).isNotEmpty();
    }
}