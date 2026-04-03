package mk.ukim.finki.backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import mk.ukim.finki.backend.model.enums.HttpMethod;

public record CreateApiDto(
        @NotBlank
        String name,

        @NotBlank
        @Pattern(regexp = "https?://.+", message = "Invalid URL format")
        String url,

        @NotNull
        HttpMethod method
) {
}
