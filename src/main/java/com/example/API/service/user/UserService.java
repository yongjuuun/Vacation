package com.example.API.service.user;

import com.example.API.domain.user.User;
import com.example.API.web.dto.JoinRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> findAll();

    User findById(Long userId);
    User findByUsername(String username);
    boolean existsByUsername(String username);

    Long save(JoinRequestDto joinRequestDto);

    User getUser();
}
