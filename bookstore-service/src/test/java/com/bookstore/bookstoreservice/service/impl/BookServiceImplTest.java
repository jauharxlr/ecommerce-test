package com.bookstore.bookstoreservice.service.impl;

import com.bookstore.bookstoreservice.model.dto.BookRequestDto;
import com.bookstore.bookstoreservice.model.dto.BookResponseDto;
import com.bookstore.bookstoreservice.model.entity.BookEntity;
import com.bookstore.bookstoreservice.model.entity.TypeEntity;
import com.bookstore.bookstoreservice.repository.BookRepository;
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
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private TypeServiceImpl typeService;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void addBook_shouldSaveBookEntity() {
        BookRequestDto bookRequestDto = new BookRequestDto();
        when(bookRepository.findByIsbn(any())).thenReturn(Optional.empty());
        when(typeService.getName(any())).thenReturn(TypeEntity.builder().name("fiction").build());
        when(bookRepository.save(any())).thenReturn(new BookEntity());
        bookService.addBook(bookRequestDto);
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void updateBook_shouldUpdateExistingBookEntity() {
        Long bookId = 1L;
        BookRequestDto bookRequestDto = new BookRequestDto();
        BookEntity existingBookEntity = BookEntity.builder().type(TypeEntity.builder().name("fiction").build()).build();
        existingBookEntity.setId(bookId);
        when(bookRepository.findByIsbn(any())).thenReturn(Optional.of(existingBookEntity));
        when(typeService.getName(any())).thenReturn(TypeEntity.builder().name("fiction").build());
        when(bookRepository.save(any())).thenReturn(existingBookEntity);
        bookService.updateBook(bookId, bookRequestDto);
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void deleteBook_shouldDeleteExistingBookEntity() {
        BookEntity existingBookEntity = new BookEntity();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(existingBookEntity));
        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).delete(existingBookEntity);
    }

    @Test
    void getBook_shouldReturnBookResponseDto() {
        BookEntity existingBookEntity = BookEntity.builder().type(TypeEntity.builder().name("fiction").build()).build();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(existingBookEntity));
        BookResponseDto bookResponseDto = bookService.getBook(1L);
        Assertions.assertThat(bookResponseDto).isNotNull();
    }

    @Test
    void getAllBooks_shouldReturnListOfBookResponseDto() {
        List<BookEntity> bookEntityList = Arrays.asList(BookEntity.builder().type(TypeEntity.builder().name("fiction").build()).build());
        when(bookRepository.findAll()).thenReturn(bookEntityList);
        List<BookResponseDto> bookResponseDtoList = bookService.getAllBooks();
        Assertions.assertThat(bookResponseDtoList).isNotEmpty();
    }

    @Test
    void getAllBooksByAuthor_shouldReturnListOfBookResponseDto() {
        String author = "Author";
        List<BookEntity> bookEntityList = Arrays.asList(BookEntity.builder().type(TypeEntity.builder().name("fiction").build()).build());
        when(bookRepository.findAllByAuthor(anyString())).thenReturn(bookEntityList);
        List<BookResponseDto> bookResponseDtoList = bookService.getAllBooksByAuthor(author);
        Assertions.assertThat(bookResponseDtoList).isNotEmpty();
    }

    @Test
    void getAllBooksByType_shouldReturnListOfBookResponseDto() {
        List<BookEntity> bookEntityList = Arrays.asList(BookEntity.builder().type(TypeEntity.builder().name("fiction").build()).build());
        when(typeService.getName(anyString())).thenReturn(TypeEntity.builder().name("fiction").build());
        when(bookRepository.findAllByType(any())).thenReturn(bookEntityList);
        List<BookResponseDto> bookResponseDtoList = bookService.getAllBooksByType("type");
        Assertions.assertThat(bookResponseDtoList).isNotEmpty();
    }

    @Test
    void getAllBooksByName_shouldReturnListOfBookResponseDto() {
        List<BookEntity> bookEntityList = Arrays.asList(BookEntity.builder().type(TypeEntity.builder().name("fiction").build()).build());
        when(bookRepository.findByNameContaining(anyString())).thenReturn(bookEntityList);
        List<BookResponseDto> bookResponseDtoList = bookService.getAllBooksByName("book");
        Assertions.assertThat(bookResponseDtoList).isNotEmpty();
    }

    @Test
    void getByIsbn_shouldReturnBookEntity() {
        BookEntity existingBookEntity = new BookEntity();
        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.of(existingBookEntity));
        BookEntity bookEntity = bookService.getByIsbn("1234567890");
        Assertions.assertThat(bookEntity).isNotNull();
    }
}