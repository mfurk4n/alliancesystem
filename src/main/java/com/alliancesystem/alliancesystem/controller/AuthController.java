package com.alliancesystem.alliancesystem.controller;

import com.alliancesystem.alliancesystem.dto.request.RefreshTokenRequest;
import com.alliancesystem.alliancesystem.dto.request.UserLoginRequest;
import com.alliancesystem.alliancesystem.dto.request.UserRegisterRequest;
import com.alliancesystem.alliancesystem.dto.response.AuthResponse;
import com.alliancesystem.alliancesystem.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication Related APIs")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    @Operation(summary = "User login")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(authService.loginUser(userLoginRequest));
    }

    @PostMapping("/register")
    @Operation(summary = "User registration")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        return new ResponseEntity<>(authService.registerUser(userRegisterRequest), HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh token")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthResponse authResponse = authService.refreshToken(refreshTokenRequest);
        if (authResponse.getJwtToken() == null) {
            return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        }

    }
}
