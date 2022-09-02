package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.UserDto;
import com.example.demo.model.UserEntity;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            // 요청을 이용해 저장할 사용자 생성
            UserEntity user = UserEntity.builder()
                    .email(userDto.getEmail())
                    .userName(userDto.getUsername())
                    .password(userDto.getPassword())
                    .build();

            // 서비스를 이용해 repository에 사용자 저장
            UserEntity registeredUser = userService.create(user);
            UserDto responseUserDto = UserDto.builder()
                    .email(registeredUser.getEmail())
                    .id(registeredUser.getId())
                    .username(registeredUser.getUserName())
                    .build();

            return ResponseEntity.ok().body(responseUserDto);
        } catch (Exception e) {
            // 사용자 정보는 항상 하나이므로 그냥 UserDto 리턴
            ResponseDto responseDto = ResponseDto.builder()
                    .error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDto);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto) {
        UserEntity user = userService.getByCredentials(
                userDto.getEmail(), userDto.getPassword()); // 로그인 인증 메서드 사용

        if (user != null) { // 인증 성공
            final UserDto responseUserDto = UserDto.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .build();
            return ResponseEntity.ok().body(responseUserDto);
        } else { // 인증 실패, 400 error 리턴
            ResponseDto responseDto = ResponseDto.builder()
                    .error("Login failed")
                    .build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDto);
        }
    }
}
