package mk.ukim.finki.backend.model.dto;

public record UserRegisterRequestDto(
        String username,
        String email,
        String password
) {
}
