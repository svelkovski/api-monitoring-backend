package mk.ukim.finki.backend.model.dto;

import mk.ukim.finki.backend.model.domain.Api;
import mk.ukim.finki.backend.model.domain.Checks;
import mk.ukim.finki.backend.model.enums.HttpMethod;

public record CheckedApiDto(
        String name,
        String url,
        HttpMethod method,
        String status,
        Long responseTime,
        int responseCode,
        String checkedAt
) {
    public static CheckedApiDto from(Api api, Checks latestCheck) {
        if (latestCheck == null) {
            return new CheckedApiDto(
                    api.getName(),
                    api.getUrl(),
                    api.getMethod(),
                    api.getStatus(),
                    null,
                    0,
                    null
            );
        } else {
            return new CheckedApiDto(
                    api.getName(),
                    api.getUrl(),
                    api.getMethod(),
                    getApiStatus(latestCheck),
                    latestCheck.getResponseTime(),
                    latestCheck.getResponseCode(),
                    latestCheck.getCheckedAt().toString()
            );
        }
    }

    private static String getApiStatus(Checks latestCheck) {
        if (latestCheck.getResponseCode() >= 200 && latestCheck.getResponseCode() < 300) {
            return "UP";
        } else {
            return "DOWN";
        }
    }
}
