package com.example.API.web.dto.request;

import com.example.API.domain.user.Role;
import com.example.API.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionUser {
    private Long id;
    private String username;
    private String password;
    private Role role;


    public static SessionUser fromEntity(User user) {
        return new SessionUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }
}
