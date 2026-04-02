package mk.ukim.finki.backend.service.application;

import mk.ukim.finki.backend.model.dto.UserLoginRequestDto;
import mk.ukim.finki.backend.model.dto.UserLoginResponseDto;
import mk.ukim.finki.backend.model.dto.UserRegisterRequestDto;
import mk.ukim.finki.backend.model.dto.UserRegisterResponseDto;

import java.util.Optional;

public interface UserApplicationService {
    Optional<UserRegisterResponseDto> register(UserRegisterRequestDto userRegisterRequestDto);

    Optional<UserLoginResponseDto> login(UserLoginRequestDto userLoginRequestDto);
}
