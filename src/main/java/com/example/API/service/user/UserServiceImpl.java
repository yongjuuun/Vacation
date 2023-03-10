package com.example.API.service.user;

import com.example.API.domain.user.User;
import com.example.API.exception.UserNotFoundException;
import com.example.API.repository.UserRepository;
import com.example.API.web.dto.JoinRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return (User) userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    @Override
    public Long save(JoinRequestDto joinRequestDto) {
        User user = joinRequestDto.toEntity();
        return userRepository.save(user).getId();
    }

    @Override
    public User getUser() {
        String currentUserName  = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(currentUserName).orElseThrow(() ->
                new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
