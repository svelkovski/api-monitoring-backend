package mk.ukim.finki.backend.model.dto;

import mk.ukim.finki.backend.model.domain.Api;
import mk.ukim.finki.backend.model.enums.HttpMethod;

public record DisplayApiDto(
        Long id,
        String name,
        String url,
        HttpMethod method,
        String status
) {
    public static DisplayApiDto from(Api api) {
        return new DisplayApiDto(
                api.getId(),
                api.getName(),
                api.getUrl(),
                api.getMethod(),
                api.getStatus()
        );
    }
}
