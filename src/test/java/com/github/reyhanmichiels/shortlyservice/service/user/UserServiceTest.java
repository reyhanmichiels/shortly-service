package com.github.reyhanmichiels.shortlyservice.service.user;

import com.github.reyhanmichiels.shortlyservice.business.dto.user.UserDTO;
import com.github.reyhanmichiels.shortlyservice.business.entity.User;
import com.github.reyhanmichiels.shortlyservice.business.repository.user.UserRepository;
import com.github.reyhanmichiels.shortlyservice.business.service.user.UserServiceImpl;
import com.github.reyhanmichiels.shortlyservice.handler.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUserByID_WhenUserExists_ShouldReturnUserDTO() {
        User user = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .build();

        when(userRepository.findOne(any(Specification.class))).thenReturn(Optional.of(user));

        UserDTO userDTO = userService.getUserByID(1L);

        assertNotNull(userDTO);
        assertEquals(1L, userDTO.getId());
        assertEquals("John Doe", userDTO.getName());
        assertEquals("john@example.com", userDTO.getEmail());
    }

    @Test
    void getUserByID_WhenUserDoesNotExist_ShouldThrowResourceNotFoundException() {
        when(userRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.getUserByID(1L)
        );

        assertEquals("User not found with id: 1", exception.getMessage());
    }
}
