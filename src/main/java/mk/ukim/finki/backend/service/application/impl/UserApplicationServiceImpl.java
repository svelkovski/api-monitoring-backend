package mk.ukim.finki.backend.service.application.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.backend.model.domain.User;
import mk.ukim.finki.backend.model.dto.UserLoginRequestDto;
import mk.ukim.finki.backend.model.dto.UserLoginResponseDto;
import mk.ukim.finki.backend.model.dto.UserRegisterRequestDto;
import mk.ukim.finki.backend.model.dto.UserRegisterResponseDto;
import mk.ukim.finki.backend.service.domain.JwtService;
import mk.ukim.finki.backend.service.application.UserApplicationService;
import mk.ukim.finki.backend.service.domain.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public Optional<UserRegisterResponseDto> register(UserRegisterRequestDto dto) {
        User user = userService.register(dto.username(), dto.email(), dto.password());
        UserRegisterResponseDto userRegisterResponseDto = UserRegisterResponseDto.from(user);
        return Optional.of(userRegisterResponseDto);
    }

    @Override
    public Optional<UserLoginResponseDto> login(UserLoginRequestDto dto) {
        User user = userService.login(dto.username(), dto.password());
        String token = jwtService.generateToken(user);
        return Optional.of(new UserLoginResponseDto(token));
    }
}
