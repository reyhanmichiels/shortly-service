package com.github.reyhanmichiels.shortlyservice.business.service.url;

import com.github.reyhanmichiels.shortlyservice.business.dto.url.CreateUrlRequest;
import com.github.reyhanmichiels.shortlyservice.business.dto.user.UserDTO;

public interface UrlService {

    void create(UserDTO authUser, CreateUrlRequest param);

    String getRedirectUrl(String shortUrl);

}
