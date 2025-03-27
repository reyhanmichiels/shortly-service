package com.github.reyhanmichiels.shortlyservice.business.service.auth;

import com.github.reyhanmichiels.shortlyservice.business.dto.auth.LoginRequest;
import com.github.reyhanmichiels.shortlyservice.business.dto.auth.LoginResponse;
import com.github.reyhanmichiels.shortlyservice.business.dto.auth.RefreshTokenRequest;
import com.github.reyhanmichiels.shortlyservice.business.dto.auth.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest param);

    LoginResponse login(LoginRequest param);

    LoginResponse refreshToken(RefreshTokenRequest param);

}