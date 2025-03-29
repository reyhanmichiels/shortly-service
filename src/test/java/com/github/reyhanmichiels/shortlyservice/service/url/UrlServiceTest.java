package com.github.reyhanmichiels.shortlyservice.service.url;

import com.github.reyhanmichiels.shortlyservice.business.dto.url.CreateUrlRequest;
import com.github.reyhanmichiels.shortlyservice.business.dto.url.RedirectPrivateUrlRequest;
import com.github.reyhanmichiels.shortlyservice.business.dto.user.UserDTO;
import com.github.reyhanmichiels.shortlyservice.business.entity.Url;
import com.github.reyhanmichiels.shortlyservice.business.repository.url.UrlRepository;
import com.github.reyhanmichiels.shortlyservice.business.service.url.UrlServiceImpl;
import com.github.reyhanmichiels.shortlyservice.handler.exception.DuplicateResourceException;
import com.github.reyhanmichiels.shortlyservice.handler.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UrlServiceImpl urlService;

    private final UserDTO authUser = UserDTO.builder()
            .id(1L)
            .build();

    @Test
    void create_WhenValidRequest_ShouldSaveUrl() {
        CreateUrlRequest param = CreateUrlRequest.builder()
                .shortUrl("shortUrl")
                .originalUrl("originalUrl")
                .password("password")
                .build();
        Url url = Url.builder()
                .shortUrl(param.getShortUrl())
                .originalUrl(param.getOriginalUrl())
                .userId(authUser.getId())
                .password(param.getPassword())
                .createdBy(authUser.getId().toString())
                .build();

        when(passwordEncoder.encode(param.getPassword())).thenReturn(param.getPassword());

        urlService.create(authUser, param);
        verify(urlRepository).save(url);
    }

    @Test
    void createUrl_WhenValidRequest_WithoutPassword_ShouldSaveUrl() {
        CreateUrlRequest param = CreateUrlRequest.builder()
                .shortUrl("shortUrl")
                .originalUrl("originalUrl")
                .build();
        Url url = Url.builder()
                .shortUrl(param.getShortUrl())
                .originalUrl(param.getOriginalUrl())
                .userId(authUser.getId())
                .createdBy(authUser.getId().toString())
                .build();

        urlService.create(authUser, param);
        verify(urlRepository).save(url);
    }

    @Test
    void createUrl_WhenShortUrlAlreadyExist_ShouldThrowDuplicateResourceException() {
        CreateUrlRequest param = CreateUrlRequest.builder()
                .shortUrl("shortUrl")
                .originalUrl("originalUrl")
                .build();
        Url url = Url.builder()
                .shortUrl(param.getShortUrl())
                .originalUrl(param.getOriginalUrl())
                .userId(authUser.getId())
                .createdBy(authUser.getId().toString())
                .build();

        doThrow(DataIntegrityViolationException.class).when(urlRepository).save(url);
        assertThrows(DuplicateResourceException.class, () -> urlService.create(authUser, param));
    }

    @Test
    void getRedirectUrl_WhenUrlExistsAndNoPassword_ShouldReturnOriginalUrl() {
        String shortUrl = "shortUrl";
        Url url = Url.builder()
                .shortUrl(shortUrl)
                .originalUrl("originalUrl")
                .isActive(true)
                .build();

        when(urlRepository.findOne(any(Specification.class))).thenReturn(Optional.of(url));

        String result = urlService.getRedirectUrl(shortUrl);
        assertEquals("originalUrl", result);
    }

    @Test
    void getRedirectUrl_WhenUrlExistsAndHasPassword_ShouldReturnAuthUrl() {
        String shortUrl = "shortUrl";
        Url url = Url.builder()
                .shortUrl(shortUrl)
                .originalUrl("originalUrl")
                .password("password")
                .isActive(true)
                .build();

        when(urlRepository.findOne(any(Specification.class))).thenReturn(Optional.of(url));

        String result = urlService.getRedirectUrl(shortUrl);
        assertEquals("/r/shortUrl/input-password", result);
    }

    @Test
    void getRedirectUrl_WhenUrlNotFound_ShouldThrowResourceNotFoundException() {
        String shortUrl = "shortUrl";

        when(urlRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> urlService.getRedirectUrl(shortUrl));
    }

    @Test
    void getRedirectPrivateUrl_WhenUrlExistsAndPasswordMatches_ShouldReturnOriginalUrl() {
        String shortUrl = "shortUrl";
        String password = "password";
        RedirectPrivateUrlRequest param = RedirectPrivateUrlRequest.builder()
                .shortUrl(shortUrl)
                .password(password)
                .build();
        Url url = Url.builder()
                .shortUrl(shortUrl)
                .originalUrl("originalUrl")
                .password("encodedPassword")
                .isActive(true)
                .build();

        when(urlRepository.findOne(any(Specification.class))).thenReturn(Optional.of(url));
        when(passwordEncoder.matches(password, url.getPassword())).thenReturn(true);

        String result = urlService.getRedirectPrivateUrl(param);
        assertEquals("originalUrl", result);
    }

    @Test
    void getRedirectPrivateUrl_WhenUrlExistsAndPasswordDoesNotMatch_ShouldReturnErrorUrl() {
        String shortUrl = "shortUrl";
        String password = "wrongPassword";
        RedirectPrivateUrlRequest param = RedirectPrivateUrlRequest.builder()
                .shortUrl(shortUrl)
                .password(password)
                .build();
        Url url = Url.builder()
                .shortUrl(shortUrl)
                .originalUrl("originalUrl")
                .password("encodedPassword")
                .isActive(true)
                .build();

        when(urlRepository.findOne(any(Specification.class))).thenReturn(Optional.of(url));
        when(passwordEncoder.matches(password, url.getPassword())).thenReturn(false);

        String result = urlService.getRedirectPrivateUrl(param);
        assertEquals("/r/shortUrl/input-password?error=Invalid password", result);
    }

    @Test
    void getRedirectPrivateUrl_WhenUrlNotFound_ShouldThrowResourceNotFoundException() {
        RedirectPrivateUrlRequest param = RedirectPrivateUrlRequest.builder()
                .shortUrl("nonExistentUrl")
                .password("password")
                .build();

        when(urlRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> urlService.getRedirectPrivateUrl(param));
    }
}