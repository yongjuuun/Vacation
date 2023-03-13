package com.example.API.web.dto.request;

import com.example.API.domain.user.Role;
import com.example.API.domain.user.User;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinRequestDto {
    private String username;

    private String password;

    private Role role;
    private Double availableVacDays;

    private Double requestedVacDays;

    public User toEntity() {
        return User.builder()
                .username(this.username)
                .password(this.password)
                .availableVacDays(this.availableVacDays)
                .requestedVacDays(this.requestedVacDays)
                .build();
    }
}

