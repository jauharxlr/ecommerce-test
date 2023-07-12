package com.bookstore.bookstoreservice.controller;

import com.bookstore.bookstoreservice.constant.AppConstants;
import com.bookstore.bookstoreservice.model.dto.BookRequestDto;
import com.bookstore.bookstoreservice.model.dto.BookResponseDto;
import com.bookstore.bookstoreservice.service.BookService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.Endpoints.BOOKS)
public class BooksController {

    private final BookService bookService;

    @ApiOperation(value = "Add book", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookResponseDto> addBook(@Valid @RequestBody BookRequestDto bookRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(bookRequestDto));
    }

    @ApiOperation(value = "Update book", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequestDto bookRequestDto) {
        return ResponseEntity.ok(bookService.updateBook(id, bookRequestDto));
    }
    @ApiOperation(value = "Delete book", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @ApiOperation(value = "Get book by id", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @ApiOperation(value = "Get all books", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        List<BookResponseDto> list = bookService.getAllBooks();
        if(CollectionUtils.isEmpty(list)){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(list);
    }

    @ApiOperation(value = "Get books by author", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @GetMapping("/author/{author}")
    public ResponseEntity<List<BookResponseDto>> getAllBooksByAuthor(@PathVariable String author) {
        List<BookResponseDto> list = bookService.getAllBooksByAuthor(author);
        if(CollectionUtils.isEmpty(list)){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(list);
        }
        return ResponseEntity.ok(list);
    }

    @ApiOperation(value = "Get books by type", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @GetMapping("/type/{type}")
    public ResponseEntity<List<BookResponseDto>> getAllBooksByType(@PathVariable String type) {
        List<BookResponseDto> list = bookService.getAllBooksByType(type);
        if(CollectionUtils.isEmpty(list)){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(list);
        }
        return ResponseEntity.ok(list);
    }

    @ApiOperation(value = "Get books by name", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<List<BookResponseDto>> getAllBooksByName(@PathVariable String name) {
        List<BookResponseDto> list = bookService.getAllBooksByName(name);
        if(CollectionUtils.isEmpty(list)){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(list);
        }
        return ResponseEntity.ok(list);
    }
}
