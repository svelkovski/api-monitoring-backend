package mk.ukim.finki.backend.model.dto;

import mk.ukim.finki.backend.model.domain.Api;
import mk.ukim.finki.backend.model.enums.HttpMethod;

public record DisplayApiDto(
        String name,
        String url,
        HttpMethod method,
        String status
) {
    public static DisplayApiDto from(Api api) {
        return new DisplayApiDto(
                api.getName(),
                api.getUrl(),
                api.getMethod(),
                api.getStatus()
        );
    }
}
