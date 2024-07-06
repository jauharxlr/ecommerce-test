package com.ecommerce.userservice.controller;

import com.ecommerce.securitymodule.service.SecurityUserService;
import com.ecommerce.securitymodule.util.JwtUtil;
import com.ecommerce.userservice.constant.AppConstants;
import com.ecommerce.userservice.exception.UserException;
import com.ecommerce.userservice.model.dto.AuthRequestDto;
import com.ecommerce.userservice.model.dto.AuthResponseDto;
import com.ecommerce.userservice.service.UserService;
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
    private final UserService userService;

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

        return ResponseEntity.ok(AuthResponseDto.builder().userDetails(userService.getUserBy(authRequestDto.getEmailId()).get()).jwt(jwt).build());
    }

}
