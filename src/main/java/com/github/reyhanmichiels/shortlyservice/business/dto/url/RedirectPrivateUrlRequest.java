package com.github.reyhanmichiels.shortlyservice.business.dto.url;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RedirectPrivateUrlRequest {

    private String shortUrl;

    private String password;

}
