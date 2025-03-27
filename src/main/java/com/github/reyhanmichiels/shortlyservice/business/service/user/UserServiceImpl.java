package com.github.reyhanmichiels.shortlyservice.business.service.user;

import com.github.reyhanmichiels.shortlyservice.business.dto.user.UserDTO;
import com.github.reyhanmichiels.shortlyservice.business.dto.user.UserParam;
import com.github.reyhanmichiels.shortlyservice.handler.exception.ResourceNotFoundException;
import com.github.reyhanmichiels.shortlyservice.business.repository.user.UserRepository;
import com.github.reyhanmichiels.shortlyservice.business.repository.user.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserDTO getUserByID(Long id) {
        return this.userRepository.findOne(UserSpecification.param(UserParam.builder().id(id).build()))
                .map(user -> UserDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .build())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

}
