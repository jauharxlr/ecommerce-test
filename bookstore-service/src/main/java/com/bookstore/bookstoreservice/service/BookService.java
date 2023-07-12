package com.bookstore.bookstoreservice.service;

import com.bookstore.bookstoreservice.model.dto.BookRequestDto;
import com.bookstore.bookstoreservice.model.dto.BookResponseDto;

import java.util.List;

public interface BookService {
    BookResponseDto addBook(BookRequestDto bookRequestDto);
    BookResponseDto updateBook(Long id, BookRequestDto bookRequestDto);
    void deleteBook(Long id);
    BookResponseDto getBook(Long id);
    List<BookResponseDto> getAllBooks();
    List<BookResponseDto> getAllBooksByAuthor(String author);
    List<BookResponseDto> getAllBooksByType(String type);
    List<BookResponseDto> getAllBooksByName(String name);
}
