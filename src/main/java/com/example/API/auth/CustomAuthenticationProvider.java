package com.example.API.auth;

import com.example.API.domain.user.Role;
import com.example.API.service.user.UserService;
import com.example.API.web.dto.request.JoinRequestDto;
import com.example.API.web.dto.request.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final PrincipalUserDetailsService principalUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession httpSession;
    private final UserService userService;
    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        boolean hasUser = userService.existsByUsername(username);

        if (!hasUser) {
            userService.save(JoinRequestDto.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .role(Role.USER)
//                    .availableVacDays(15.0)
//                    .requestedVacDays(0.0)
                    .build());
        }

        PrincipalUserDetails userDetails =
                (PrincipalUserDetails) principalUserDetailsService.loadUserByUsername(username);

        if (!this.passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        httpSession.setAttribute("user", SessionUser.fromEntity(userDetails.getUser()));

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
