package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.constant.AppConstants;
import com.ecommerce.productservice.model.dto.ProductRequestDto;
import com.ecommerce.productservice.model.dto.ProductResponseDto;
import com.ecommerce.productservice.service.ProductService;
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
@RequestMapping(AppConstants.Endpoints.PRODUCT)
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Add product", notes = "Requires JWT authorization")
    public ResponseEntity<ProductResponseDto> addProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(productRequestDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Update product", notes = "Requires JWT authorization")
    public ResponseEntity<ProductResponseDto> editProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity.ok(productService.updateProduct(id, productRequestDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Delete product", notes = "Requires JWT authorization")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Get product by id", notes = "Requires JWT authorization")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @GetMapping
    @ApiOperation(value = "Get all products", notes = "Requires JWT authorization")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> list = productService.getAllProducts();
        if(CollectionUtils.isEmpty(list)){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(list);
    }
    @GetMapping("/search/{name}")
    @ApiOperation(value = "Get product by name", notes = "Requires JWT authorization")
    public ResponseEntity<List<ProductResponseDto>> getProductsByName(@PathVariable String name) {
        List<ProductResponseDto> list = productService.getAllProductsByName(name);
        if(CollectionUtils.isEmpty(list)){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(list);
        }
        return ResponseEntity.ok(list);
    }
}
