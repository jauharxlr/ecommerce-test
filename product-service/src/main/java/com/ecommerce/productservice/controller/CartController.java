package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.constant.AppConstants;
import com.ecommerce.productservice.model.dto.AddToCartRequestDto;
import com.ecommerce.productservice.model.dto.CartResponseDto;
import com.ecommerce.productservice.model.dto.CheckoutResponseDto;
import com.ecommerce.productservice.service.CartService;
import com.ecommerce.securitymodule.util.AuthUtility;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.Endpoints.PRODUCT + AppConstants.Endpoints.CART)
public class CartController {

    private final CartService cartService;
    private final AuthUtility authUtility;
    
    @PostMapping
    @ApiOperation(value = "add to cart", notes = "Requires JWT authorization")
    public ResponseEntity<Void> addToCart(@Valid @RequestBody AddToCartRequestDto addToCartRequestDto) {
        String username = authUtility.getLoggedInUsername();
        cartService.addToCart(username, addToCartRequestDto);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    @ApiOperation(value = "get cart", notes = "Requires JWT authorization")
    public ResponseEntity<CartResponseDto> getCart() {
        String username = authUtility.getLoggedInUsername();
        return ResponseEntity.ok(cartService.getCart(username));
    }
    @DeleteMapping("/{id}/{quantity}")
    @ApiOperation(value = "Delete an item or reduce a item's quantity of logged in user", notes = "Requires JWT authorization")
    public ResponseEntity<Void> deleteFromCart(@PathVariable Long id, @PathVariable Integer quantity) {
        String username = authUtility.getLoggedInUsername();
        cartService.deleteFromCart(username, id, quantity);
        return ResponseEntity.accepted().build();
    }
    @GetMapping(AppConstants.Endpoints.CHECKOUT)
    @ApiOperation(value = "Checkout cart of a logged user", notes = "Requires JWT authorization")
    public ResponseEntity<CheckoutResponseDto> checkout() {
        String username = authUtility.getLoggedInUsername();
        return ResponseEntity.ok(cartService.checkout(username));
    }
    @DeleteMapping
    @ApiOperation(value = "Clear complete cart of logged in user", notes = "Requires JWT authorization")
    public ResponseEntity<Void> clearCart(){
        String username = authUtility.getLoggedInUsername();
        cartService.clearCart(username);
        return ResponseEntity.ok().build();
    }
}
