package mk.ukim.finki.backend.model.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "checks")
public class Checks extends BaseEntity {

    @Column(nullable = false)
    private Long responseTime;

    private int responseCode;

    @Column(nullable = false)
    private LocalDateTime checkedAt;

    @ManyToOne
    @JoinColumn(name = "api_id", nullable = false)
    private Api api;

    @Builder
    public Checks(Long responseTime, int responseCode, Api api) {
        this.responseTime = responseTime;
        this.responseCode = responseCode;
        this.checkedAt = LocalDateTime.now();
        this.api = api;
    }
}
