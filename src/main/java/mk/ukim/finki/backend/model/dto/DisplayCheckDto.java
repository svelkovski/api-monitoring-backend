package mk.ukim.finki.backend.model.dto;

import mk.ukim.finki.backend.model.domain.Checks;

public record DisplayCheckDto(
        Long responseTime,
        int responseCode,
        String checkedAt
) {
    public static DisplayCheckDto from(Checks check) {
        return new DisplayCheckDto(
                check.getResponseTime(),
                check.getResponseCode(),
                check.getCheckedAt().toString());
    }
}
