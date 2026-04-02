package mk.ukim.finki.backend.model.dto;

public record UserLoginRequestDto(
        String username,
        String password
) {
}
