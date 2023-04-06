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

    public User toEntity() {
        return User.builder()
                .username(this.username)
                .password(this.password)
                .build();
    }
}

