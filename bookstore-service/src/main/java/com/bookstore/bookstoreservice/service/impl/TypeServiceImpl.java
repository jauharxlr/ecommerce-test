package com.bookstore.bookstoreservice.service.impl;

import com.bookstore.bookstoreservice.constant.AppConstants;
import com.bookstore.bookstoreservice.exception.TypeException;
import com.bookstore.bookstoreservice.model.dto.TypeRequestDto;
import com.bookstore.bookstoreservice.model.dto.TypeResponseDto;
import com.bookstore.bookstoreservice.model.entity.TypeEntity;
import com.bookstore.bookstoreservice.repository.TypeRepository;
import com.bookstore.bookstoreservice.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    @Override
    public TypeResponseDto addType(TypeRequestDto bookRequestDto) {
        if(typeRepository.findByNameOrPromotionalCode(bookRequestDto.getName(), bookRequestDto.getPromotionalCode()).isPresent()){
            throw new TypeException(AppConstants.ErrorMessage.NAME_AND_PROMO_BE_UNIQUE);
        }
        return TypeResponseDto.toDto(typeRepository.save(bookRequestDto.toEntity()));
    }

    @Override
    public TypeResponseDto updateType(Long id, TypeRequestDto bookRequestDto) {
        Optional<TypeEntity> typeEntityOptional = typeRepository
                .findByNameOrPromotionalCode(bookRequestDto.getName(), bookRequestDto.getPromotionalCode());
        if(typeEntityOptional.isPresent() && typeEntityOptional.get().getId()!=id){
            throw new TypeException(AppConstants.ErrorMessage.NAME_AND_PROMO_BE_UNIQUE);
        }
        TypeEntity typeEntity = bookRequestDto.toEntity();
        typeEntity.setId(id);
        return TypeResponseDto.toDto(typeRepository.save(typeEntity));
    }

    @Override
    public void deleteType(Long id) {
        typeRepository.delete(get(id));
    }

    @Override
    public TypeResponseDto getType(Long id) {
        return TypeResponseDto.toDto(get(id));
    }

    @Override
    public List<TypeResponseDto> getAllTypes() {
        return typeRepository.findAll().stream()
                .map(type->TypeResponseDto.toDto(type))
                .collect(Collectors.toList());
    }

    protected TypeEntity getByPromotionalCode(String promo){
        return typeRepository.findByPromotionalCode(promo).orElseThrow(()->new TypeException(AppConstants.ErrorMessage.PROMO_NOT_FOUND));
    }
    protected TypeEntity getName(String name){
        return typeRepository.findByName(name)
                .orElseThrow(()->new TypeException(AppConstants.ErrorMessage.TYPE_NOT_FOUND));
    }
    private TypeEntity get(Long id){
        return typeRepository.findById(id)
                .orElseThrow(()->new TypeException(AppConstants.ErrorMessage.TYPE_NOT_FOUND));
    }
}
