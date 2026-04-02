package mk.ukim.finki.backend.web.controller;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.backend.model.dto.UserLoginRequestDto;
import mk.ukim.finki.backend.model.dto.UserLoginResponseDto;
import mk.ukim.finki.backend.model.dto.UserRegisterRequestDto;
import mk.ukim.finki.backend.model.dto.UserRegisterResponseDto;
import mk.ukim.finki.backend.service.application.UserApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserApplicationService userApplicationService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> register(@RequestBody UserRegisterRequestDto dto) {
        return userApplicationService.register(dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto dto) {
        return userApplicationService.login(dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
