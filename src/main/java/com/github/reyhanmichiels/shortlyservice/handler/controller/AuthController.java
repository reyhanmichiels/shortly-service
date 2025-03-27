package com.github.reyhanmichiels.shortlyservice.handler.controller;

import com.github.reyhanmichiels.shortlyservice.business.dto.auth.LoginRequest;
import com.github.reyhanmichiels.shortlyservice.business.dto.auth.LoginResponse;
import com.github.reyhanmichiels.shortlyservice.business.dto.auth.RefreshTokenRequest;
import com.github.reyhanmichiels.shortlyservice.business.dto.auth.RegisterRequest;
import com.github.reyhanmichiels.shortlyservice.business.dto.http.HttpResponse;
import com.github.reyhanmichiels.shortlyservice.business.dto.user.UserDTO;
import com.github.reyhanmichiels.shortlyservice.business.service.auth.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/api/v1/auth/register")
    public ResponseEntity<HttpResponse<Void>> register(
            HttpServletRequest request,
            @RequestBody RegisterRequest param
    ) {
        this.authService.register(param);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        HttpResponse.success(
                                request,
                                HttpStatus.CREATED,
                                "User registered successfully"
                        )
                );
    }

    @PostMapping(path = "/api/v1/auth/login")
    public ResponseEntity<HttpResponse<LoginResponse>> login(
            HttpServletRequest request,
            @RequestBody LoginRequest param
    ) {
        LoginResponse data = this.authService.login(param);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        HttpResponse.success(
                                request,
                                HttpStatus.OK,
                                "User logged in successfully",
                                data
                        )
                );
    }

    @GetMapping("/api/v1/auth/me")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<HttpResponse<UserDTO>> me(
            HttpServletRequest request,
            @AuthenticationPrincipal UserDTO userDTO
    ) {
        return ResponseEntity.ok(
                HttpResponse.success(
                        request,
                        HttpStatus.OK,
                        "User details retrieved successfully",
                        userDTO
                )
        );
    }

    @PostMapping("/api/v1/auth/refresh-token")
    public ResponseEntity<HttpResponse<LoginResponse>> refreshToken(
            HttpServletRequest request,
            @RequestBody RefreshTokenRequest refreshToken
    ) {
        LoginResponse data = this.authService.refreshToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        HttpResponse.success(
                                request,
                                HttpStatus.OK,
                                "Refresh token generated successfully",
                                data
                        )
                );
    }
}
