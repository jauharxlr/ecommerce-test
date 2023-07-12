package com.bookstore.userservice.controller;

import com.bookstore.securitymodule.service.SecurityUserService;
import com.bookstore.securitymodule.util.JwtUtil;
import com.bookstore.userservice.constant.AppConstants;
import com.bookstore.userservice.exception.UserException;
import com.bookstore.userservice.model.dto.AuthRequestDto;
import com.bookstore.userservice.model.dto.AuthResponseDto;
import com.bookstore.userservice.model.dto.UserRequestDto;
import com.bookstore.userservice.model.entity.UserEntity;
import com.bookstore.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.Endpoints.USER + AppConstants.Endpoints.AUTHENTICATE)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final SecurityUserService securityUserService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<AuthResponseDto> authenticate(@Valid @RequestBody AuthRequestDto authRequestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequestDto.getEmailId(), authRequestDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new UserException("Invalid user credentials!");
        }
        final UserDetails userDetails = securityUserService.loadUserByUsername(authRequestDto.getEmailId());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(AuthResponseDto.builder().jwt(jwt).build());
    }

}
