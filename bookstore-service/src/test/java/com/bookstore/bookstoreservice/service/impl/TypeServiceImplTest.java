package com.bookstore.bookstoreservice.service.impl;

import com.bookstore.bookstoreservice.exception.TypeException;
import com.bookstore.bookstoreservice.model.dto.TypeRequestDto;
import com.bookstore.bookstoreservice.model.dto.TypeResponseDto;
import com.bookstore.bookstoreservice.model.entity.TypeEntity;
import com.bookstore.bookstoreservice.repository.TypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.parser.Entity;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TypeServiceImplTest {

    @Mock
    private TypeRepository mockTypeRepository;

    @InjectMocks
    private TypeServiceImpl typeServiceImplUnderTest;

    @Test
    void testAddType_ThrowsTypeException() {
        final TypeRequestDto bookRequestDto = TypeRequestDto.builder()
                .name("name")
                .promotionalCode("promotionalCode")
                .build();

        final Optional<TypeEntity> typeEntityOptional = Optional.of(TypeEntity.builder()
                .id(0L)
                .name("name")
                .promotionalCode("promotionalCode")
                .discountPercentage(0.0)
                .build());
        when(mockTypeRepository.findByNameOrPromotionalCode("name", "promotionalCode")).thenReturn(typeEntityOptional);
        assertThatThrownBy(() -> typeServiceImplUnderTest.addType(bookRequestDto)).isInstanceOf(TypeException.class);
    }

    @Test
    void testAddType_TypeRepositoryFindByNameOrPromotionalCodeReturnsAbsent() {
        final TypeRequestDto bookRequestDto = TypeRequestDto.builder()
                .name("name")
                .promotionalCode("promotionalCode")
                .build();
        final TypeResponseDto expectedResult = TypeResponseDto.builder()
                .id(0L)
                .name("name")
                .promotionalCode("promotionalCode")
                .discountPercentage(0.0)
                .build();
        when(mockTypeRepository.findByNameOrPromotionalCode("name", "promotionalCode")).thenReturn(Optional.empty());
        when(mockTypeRepository.save(any())).thenReturn(new TypeEntity());
        final TypeResponseDto result = typeServiceImplUnderTest.addType(bookRequestDto);
        assertThat(result).isNotNull();
    }

    @Test
    void testUpdateType_ThrowsTypeException() {
        final TypeRequestDto bookRequestDto = TypeRequestDto.builder()
                .name("name")
                .promotionalCode("promotionalCode")
                .build();

        final Optional<TypeEntity> typeEntityOptional = Optional.of(TypeEntity.builder()
                .id(0L)
                .name("name")
                .promotionalCode("promotionalCode")
                .discountPercentage(0.0)
                .build());
        when(mockTypeRepository.findByNameOrPromotionalCode("name", "promotionalCode")).thenReturn(typeEntityOptional);

        assertThatThrownBy(() -> typeServiceImplUnderTest.updateType(10L, bookRequestDto))
                .isInstanceOf(TypeException.class);
    }

    @Test
    void testDeleteType() {
        final Optional<TypeEntity> typeEntityOptional = Optional.of(TypeEntity.builder()
                .id(0L)
                .name("name")
                .promotionalCode("promotionalCode")
                .discountPercentage(0.0)
                .build());
        when(mockTypeRepository.findById(0L)).thenReturn(typeEntityOptional);
        typeServiceImplUnderTest.deleteType(0L);
        verify(mockTypeRepository).delete(TypeEntity.builder()
                .id(0L)
                .name("name")
                .promotionalCode("promotionalCode")
                .discountPercentage(0.0)
                .build());
    }

    @Test
    void testDeleteType_TypeRepositoryFindByIdReturnsAbsent() {
        when(mockTypeRepository.findById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> typeServiceImplUnderTest.deleteType(0L)).isInstanceOf(TypeException.class);
    }

    @Test
    void testGetType() {
        final TypeResponseDto expectedResult = TypeResponseDto.builder()
                .id(0L)
                .name("name")
                .promotionalCode("promotionalCode")
                .discountPercentage(0.0)
                .build();
        final Optional<TypeEntity> typeEntityOptional = Optional.of(TypeEntity.builder()
                .id(0L)
                .name("name")
                .promotionalCode("promotionalCode")
                .discountPercentage(0.0)
                .build());
        when(mockTypeRepository.findById(0L)).thenReturn(typeEntityOptional);
        final TypeResponseDto result = typeServiceImplUnderTest.getType(0L);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetType_TypeRepositoryReturnsAbsent() {
        when(mockTypeRepository.findById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> typeServiceImplUnderTest.getType(0L)).isInstanceOf(TypeException.class);
    }

    @Test
    void testGetAllTypes() {
        final List<TypeResponseDto> expectedResult = Arrays.asList(TypeResponseDto.builder()
                .id(0L)
                .name("name")
                .promotionalCode("promotionalCode")
                .discountPercentage(0.0)
                .build());
        final List<TypeEntity> typeEntities = Arrays.asList(TypeEntity.builder()
                .id(0L)
                .name("name")
                .promotionalCode("promotionalCode")
                .discountPercentage(0.0)
                .build());
        when(mockTypeRepository.findAll()).thenReturn(typeEntities);
        final List<TypeResponseDto> result = typeServiceImplUnderTest.getAllTypes();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllTypes_TypeRepositoryReturnsNoItems() {
        when(mockTypeRepository.findAll()).thenReturn(Collections.emptyList());
        final List<TypeResponseDto> result = typeServiceImplUnderTest.getAllTypes();
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetByPromotionalCode() {
        final TypeEntity expectedResult = TypeEntity.builder()
                .id(0L)
                .name("name")
                .promotionalCode("promotionalCode")
                .discountPercentage(0.0)
                .build();
        final Optional<TypeEntity> typeEntityOptional = Optional.of(TypeEntity.builder()
                .id(0L)
                .name("name")
                .promotionalCode("promotionalCode")
                .discountPercentage(0.0)
                .build());
        when(mockTypeRepository.findByPromotionalCode("promo")).thenReturn(typeEntityOptional);
        final TypeEntity result = typeServiceImplUnderTest.getByPromotionalCode("promo");
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetByPromotionalCode_TypeRepositoryReturnsAbsent() {
        when(mockTypeRepository.findByPromotionalCode("promo")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> typeServiceImplUnderTest.getByPromotionalCode("promo"))
                .isInstanceOf(TypeException.class);
    }

    @Test
    void testGetName() {
        final TypeEntity expectedResult = TypeEntity.builder()
                .id(0L)
                .name("name")
                .promotionalCode("promotionalCode")
                .discountPercentage(0.0)
                .build();
        final Optional<TypeEntity> typeEntityOptional = Optional.of(TypeEntity.builder()
                .id(0L)
                .name("name")
                .promotionalCode("promotionalCode")
                .discountPercentage(0.0)
                .build());
        when(mockTypeRepository.findByName("name")).thenReturn(typeEntityOptional);
        final TypeEntity result = typeServiceImplUnderTest.getName("name");
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetName_TypeRepositoryReturnsAbsent() {
        when(mockTypeRepository.findByName("name")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> typeServiceImplUnderTest.getName("name")).isInstanceOf(TypeException.class);
    }
}
