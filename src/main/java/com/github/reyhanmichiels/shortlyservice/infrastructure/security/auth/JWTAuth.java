package com.github.reyhanmichiels.shortlyservice.infrastructure.security.auth;

import com.github.reyhanmichiels.shortlyservice.business.dto.user.UserDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWTAuth extends UsernamePasswordAuthenticationToken {
    public JWTAuth(UserDTO principal, String credentials) {
        super(principal, credentials);
    }

    public JWTAuth(UserDTO principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
