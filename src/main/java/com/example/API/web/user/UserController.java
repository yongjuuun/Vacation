package com.example.API.web.user;

import com.example.API.domain.user.User;
import com.example.API.service.user.UserService;
import com.example.API.web.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<User> users = userService.findAll();
        List<UserResponseDto> dto =
                users.stream().map(p -> modelMapper.map(p, UserResponseDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long userId) {
        User user = userService.findById(userId);
        UserResponseDto dto = modelMapper.map(user, UserResponseDto.class);
        return ResponseEntity.ok(dto);
    }
}

