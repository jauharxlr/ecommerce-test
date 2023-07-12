package com.bookstore.bookstoreservice.service;

import com.bookstore.bookstoreservice.model.dto.TypeRequestDto;
import com.bookstore.bookstoreservice.model.dto.TypeResponseDto;

import java.util.List;

public interface TypeService {
    TypeResponseDto addType(TypeRequestDto typeRequestDto);
    TypeResponseDto updateType(Long id, TypeRequestDto typeRequestDto);
    void deleteType(Long id);
    TypeResponseDto getType(Long id);
    List<TypeResponseDto> getAllTypes();
}
