package mk.ukim.finki.backend.model.dto;

import mk.ukim.finki.backend.model.domain.User;

public record UserRegisterResponseDto(
        String username,
        String email
) {
    public static UserRegisterResponseDto from(User user) {
        return new UserRegisterResponseDto(
                user.getUsername(),
                user.getEmail()
        );
    }
}
