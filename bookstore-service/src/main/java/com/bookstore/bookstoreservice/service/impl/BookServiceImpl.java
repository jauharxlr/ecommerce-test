package com.bookstore.bookstoreservice.service.impl;

import com.bookstore.bookstoreservice.constant.AppConstants;
import com.bookstore.bookstoreservice.exception.BookException;
import com.bookstore.bookstoreservice.model.dto.BookRequestDto;
import com.bookstore.bookstoreservice.model.dto.BookResponseDto;
import com.bookstore.bookstoreservice.model.entity.BookEntity;
import com.bookstore.bookstoreservice.repository.BookRepository;
import com.bookstore.bookstoreservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final TypeServiceImpl typeService;

    @Override
    public BookResponseDto addBook(BookRequestDto bookRequestDto) {
        if(bookRepository.findByIsbn(bookRequestDto.getIsbn()).isPresent()){
            throw new BookException(AppConstants.ErrorMessage.ISBN_ALREADY_PRESENT);
        }
        BookEntity bookEntity = bookRequestDto.toEntity(typeService.getName(bookRequestDto.getType()));
        bookRepository.save(bookEntity);
        return BookResponseDto.fromEntity(bookEntity);
    }

    @Override
    public BookResponseDto updateBook(Long id, BookRequestDto bookRequestDto) {
        Optional<BookEntity> bookEntityOptional = bookRepository.findByIsbn(bookRequestDto.getIsbn());
        if(bookEntityOptional.isPresent() && bookEntityOptional.get().getId()!=id){
            throw new BookException(AppConstants.ErrorMessage.ISBN_ALREADY_PRESENT);
        }
        BookEntity bookEntity = bookRequestDto.toEntity(typeService.getName(bookRequestDto.getType()));
        bookEntity.setId(id);
        bookRepository.save(bookEntity);
        return BookResponseDto.fromEntity(bookEntity);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.delete(get(id));
    }

    @Override
    public BookResponseDto getBook(Long id) {
        return BookResponseDto.fromEntity(get(id));
    }

    @Override
    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookEntity -> BookResponseDto.fromEntity(bookEntity))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponseDto> getAllBooksByAuthor(String author) {
        return bookRepository.findAllByAuthor(author).stream()
                .map(bookEntity -> BookResponseDto.fromEntity(bookEntity))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponseDto> getAllBooksByType(String type) {
        return bookRepository.findAllByType(typeService.getName(type)).stream()
                .map(bookEntity -> BookResponseDto.fromEntity(bookEntity))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponseDto> getAllBooksByName(String name) {
        return bookRepository.findByNameContaining(name).stream()
                .map(bookEntity -> BookResponseDto.fromEntity(bookEntity))
                .collect(Collectors.toList());
    }

    private BookEntity get(Long id){
        return bookRepository.findById(id).orElseThrow(()->
                new BookException(AppConstants.ErrorMessage.BOOK_NOT_FOUND));
    }
    protected BookEntity getByIsbn(String isbn){
        return bookRepository.findByIsbn(isbn).orElseThrow(()->
                new BookException(AppConstants.ErrorMessage.BOOK_NOT_FOUND));
    }
}
