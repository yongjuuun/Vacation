package com.example.API.web.dto.response;

import com.example.API.domain.user.Role;
import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String username;
    private Role role;
    private Double availableVacDays;
    private Double requestedVacDays;
}
