package com.github.reyhanmichiels.shortlyservice.business.service.url;

import com.github.reyhanmichiels.shortlyservice.business.dto.url.CreateUrlRequest;
import com.github.reyhanmichiels.shortlyservice.business.dto.url.RedirectPrivateUrlRequest;
import com.github.reyhanmichiels.shortlyservice.business.dto.url.UrlParam;
import com.github.reyhanmichiels.shortlyservice.business.dto.user.UserDTO;
import com.github.reyhanmichiels.shortlyservice.business.entity.Url;
import com.github.reyhanmichiels.shortlyservice.business.repository.url.UrlRepository;
import com.github.reyhanmichiels.shortlyservice.business.repository.url.UrlSpecification;
import com.github.reyhanmichiels.shortlyservice.handler.exception.DuplicateResourceException;
import com.github.reyhanmichiels.shortlyservice.handler.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    private final PasswordEncoder passwordEncoder;

    public void create(UserDTO authUser, CreateUrlRequest param) {
        try {
            this.urlRepository.save(Url.builder()
                    .shortUrl(param.getShortUrl())
                    .originalUrl(param.getOriginalUrl())
                    .userId(authUser.getId())
                    .password(param.getPassword() != null
                            ? this.passwordEncoder.encode(param.getPassword())
                            : null
                    )
                    .createdBy(authUser.getId().toString())
                    .build()
            );
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException(e.getMessage());
        }
    }

    // TODO: handle exception if url not found and any exception
    public String getRedirectUrl(String shortUrl) {
        return this.urlRepository.findOne(
                        UrlSpecification.param(
                                UrlParam.builder()
                                        .shortUrl(shortUrl)
                                        .isActive(true)
                                        .build()
                        )
                )
                .map(url -> url.getPassword() == null
                        ? url.getOriginalUrl()
                        : String.format("/r/%s/input-password", url.getShortUrl())
                )
                .orElseThrow(() -> new ResourceNotFoundException("URL not found"));
    }

    public String getRedirectPrivateUrl(RedirectPrivateUrlRequest param) {
        return this.urlRepository.findOne(
                        UrlSpecification.param(
                                UrlParam.builder()
                                        .shortUrl(param.getShortUrl())
                                        .isActive(true)
                                        .build()
                        )
                )
                .map(url -> {
                    if (this.passwordEncoder.matches(param.getPassword(), url.getPassword())) {
                        return url.getOriginalUrl();
                    }

                    return String.format("/r/%s/input-password?error=%s", url.getShortUrl(), "Invalid password");
                })
                .orElseThrow(() -> new ResourceNotFoundException("URL not found"));
    }

}
