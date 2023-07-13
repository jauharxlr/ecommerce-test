package com.bookstore.bookstoreservice.controller;

import com.bookstore.bookstoreservice.constant.AppConstants;
import com.bookstore.bookstoreservice.model.dto.AddToCartRequestDto;
import com.bookstore.bookstoreservice.model.dto.CartResponseDto;
import com.bookstore.bookstoreservice.model.dto.CheckoutRequest;
import com.bookstore.bookstoreservice.model.dto.CheckoutResponseDto;
import com.bookstore.bookstoreservice.service.CartService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.Endpoints.STORE + AppConstants.Endpoints.CART)
public class CartController {

    private final CartService cartService;

    @ApiOperation(value = "Update a user by userId", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @PostMapping
    public ResponseEntity addToCart(@ApiIgnore Authentication authentication, @Valid @RequestBody AddToCartRequestDto addToCartRequestDto) {
        cartService.addToCart(authentication.getName(), addToCartRequestDto);
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "Get cart of logged in user", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(@ApiIgnore Authentication authentication) {
        return ResponseEntity.ok(cartService.getCart(authentication.getName()));
    }
    @ApiOperation(value = "Delete an item or reduce a item's quantity of logged in user", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @DeleteMapping("/{isbn}/{quantity}")
    public ResponseEntity deleteFromCart(@ApiIgnore Authentication authentication, @PathVariable String isbn, @PathVariable Integer quantity) {
        cartService.deleteFromCart(authentication.getName(), isbn, quantity);
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "Checkout cart of a logged user", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @GetMapping(AppConstants.Endpoints.CHECKOUT)
    public ResponseEntity<CheckoutResponseDto> checkout(@ApiIgnore Authentication authentication, CheckoutRequest checkoutRequest) {
        return ResponseEntity.ok(cartService.checkout(authentication.getName(), checkoutRequest));
    }
    @ApiOperation(value = "Clear complete cart of ;logged in user", notes = "Requires JWT authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT token", required = true,
                    dataType = "string", paramType = "header")
    })
    @DeleteMapping
    public ResponseEntity clearCart(@ApiIgnore Authentication authentication){
        cartService.clearCart(authentication.getName());
        return ResponseEntity.ok().build();
    }
}
