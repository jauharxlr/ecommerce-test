package com.bookstore.bookstoreservice.repository;

import com.bookstore.bookstoreservice.model.entity.BookEntity;
import com.bookstore.bookstoreservice.model.entity.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findAllByAuthor(String author);
    List<BookEntity> findAllByType(TypeEntity type);
    List<BookEntity> findByNameContaining(String name);
    Optional<BookEntity> findByIsbn(String isbn);
}
