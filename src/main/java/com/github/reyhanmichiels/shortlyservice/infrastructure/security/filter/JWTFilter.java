package com.github.reyhanmichiels.shortlyservice.infrastructure.security.filter;

import com.github.reyhanmichiels.shortlyservice.infrastructure.security.auth.JWTAuth;
import com.github.reyhanmichiels.shortlyservice.business.service.user.UserService;
import com.github.reyhanmichiels.shortlyservice.util.jwt.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWT jwt;

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Optional.ofNullable(request.getHeader("Authorization"))
                .filter(header -> header.startsWith("Bearer "))
                .map(header -> header.substring(7))
                .filter(token -> jwt.validateToken(token, JWT.ACCESS_TOKEN_TYPE))
                .map(token -> new JWTAuth(
                        this.userService.getUserByID(jwt.getUserIdFromToken(token)),
                        token,
                        List.of()
                ))
                .ifPresent(SecurityContextHolder.getContext()::setAuthentication);

        filterChain.doFilter(request, response);
    }
}
