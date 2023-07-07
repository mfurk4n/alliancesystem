package com.alliancesystem.alliancesystem.service;

import com.alliancesystem.alliancesystem.dto.request.RefreshTokenRequest;
import com.alliancesystem.alliancesystem.dto.request.UserLoginRequest;
import com.alliancesystem.alliancesystem.dto.request.UserRegisterRequest;
import com.alliancesystem.alliancesystem.dto.response.AuthResponse;
import com.alliancesystem.alliancesystem.model.RefreshToken;
import com.alliancesystem.alliancesystem.model.User;
import com.alliancesystem.alliancesystem.security.JwtTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final RefreshTokenService refreshTokenService;

    public AuthService(UserService userService, AuthenticationManager authenticationManager, JwtTokenUtils jwtTokenUtils, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtils = jwtTokenUtils;
        this.refreshTokenService = refreshTokenService;
    }

    public AuthResponse loginUser(UserLoginRequest userLoginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        User user = userService.getUserEntityByUsername(userLoginRequest.getUsername());
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenUtils.generateJwtToken(auth);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Logged in successfully");
        authResponse.setJwtToken(jwtToken);
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setUserId(user.getId());
        return authResponse;
    }

    public AuthResponse registerUser(UserRegisterRequest userRegisterRequest) {
        User user = userService.createUser(userRegisterRequest);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userRegisterRequest.getUsername(), userRegisterRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenUtils.generateJwtToken(auth);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Registration successful");
        authResponse.setJwtToken(jwtToken);
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setUserId(user.getId());
        return authResponse;
    }

    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        AuthResponse authResponse = new AuthResponse();
        RefreshToken token = refreshTokenService.getByUser(refreshTokenRequest.getUserId());
        if (token.getToken().equals(refreshTokenRequest.getRefreshToken()) &&
                !refreshTokenService.isRefreshExpired(token)) {
            User user = token.getUser();
            String jwtToken = jwtTokenUtils.generateJwtTokenByUserId(user.getId());
            authResponse.setMessage("Token refreshed");
            authResponse.setJwtToken(jwtToken);
            authResponse.setUserId(user.getId());
        } else {
            authResponse.setMessage("Invalid Refresh Token");
        }

        return authResponse;
    }


}
