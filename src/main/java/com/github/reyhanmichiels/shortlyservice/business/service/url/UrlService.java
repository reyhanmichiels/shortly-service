package com.github.reyhanmichiels.shortlyservice.business.service.url;

import com.github.reyhanmichiels.shortlyservice.business.dto.url.CreateUrlRequest;
import com.github.reyhanmichiels.shortlyservice.business.dto.url.RedirectPrivateUrlRequest;
import com.github.reyhanmichiels.shortlyservice.business.dto.user.UserDTO;
import io.swagger.v3.oas.annotations.security.OAuthFlows;

public interface UrlService {

    void create(UserDTO authUser, CreateUrlRequest param);

    String getRedirectUrl(String shortUrl);

    String getRedirectPrivateUrl(RedirectPrivateUrlRequest param);
}
