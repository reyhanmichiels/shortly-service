package com.github.reyhanmichiels.shortlyservice.business.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserParam {

    private Long id;

    private String name;

    private String email;

    private Boolean isActive;

    private String refreshToken;

}
