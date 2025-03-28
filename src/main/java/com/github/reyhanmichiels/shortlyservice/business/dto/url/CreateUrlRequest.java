package com.github.reyhanmichiels.shortlyservice.business.dto.url;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUrlRequest {

    @NotBlank(message = "Short URL is required")
    private String shortUrl;

    // TODO: create custom validation for url
    @NotBlank(message = "Original URL is required")
    private String originalUrl;

    private String password;

}
