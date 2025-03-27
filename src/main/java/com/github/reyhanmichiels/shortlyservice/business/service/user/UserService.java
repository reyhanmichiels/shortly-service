package com.github.reyhanmichiels.shortlyservice.business.service.user;

import com.github.reyhanmichiels.shortlyservice.business.dto.user.UserDTO;

public interface UserService {
    UserDTO getUserByID(Long id);
}
