package com.github.reyhanmichiels.shortlyservice.handler.controller;

import com.github.reyhanmichiels.shortlyservice.business.dto.http.HttpResponse;
import com.github.reyhanmichiels.shortlyservice.business.dto.url.CreateUrlRequest;
import com.github.reyhanmichiels.shortlyservice.business.dto.user.UserDTO;
import com.github.reyhanmichiels.shortlyservice.business.service.url.UrlService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Tag(name = "Url")
public class UrlController {

    private final UrlService urlService;

    @PostMapping(path = "/api/v1/urls")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<HttpResponse<Void>> register(
            HttpServletRequest request,
            @AuthenticationPrincipal UserDTO authUser,
            @Valid @RequestBody CreateUrlRequest param
    ) {
        this.urlService.create(authUser, param);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        HttpResponse.success(
                                request,
                                HttpStatus.CREATED,
                                "Url created successfully"
                        )
                );
    }

    @GetMapping(path = "{shortUrl}")
    public void redirect(
            @PathVariable String shortUrl,
            HttpServletResponse response
    ) throws IOException {
        response.sendRedirect(
                this.urlService.redirect(shortUrl)
        );
    }

}
