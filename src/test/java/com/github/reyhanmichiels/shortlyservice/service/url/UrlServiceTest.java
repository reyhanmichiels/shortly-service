package com.github.reyhanmichiels.shortlyservice.service.url;

import com.github.reyhanmichiels.shortlyservice.business.dto.url.CreateUrlRequest;
import com.github.reyhanmichiels.shortlyservice.business.dto.user.UserDTO;
import com.github.reyhanmichiels.shortlyservice.business.entity.Url;
import com.github.reyhanmichiels.shortlyservice.business.repository.url.UrlRepository;
import com.github.reyhanmichiels.shortlyservice.business.service.url.UrlServiceImpl;
import com.github.reyhanmichiels.shortlyservice.handler.exception.DuplicateResourceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceImplTest {

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
}