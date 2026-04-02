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

    private int response_code;

    @Column(nullable = false)
    private LocalDateTime checked_at;

    @ManyToOne
    @JoinColumn(name = "api_id", nullable = false)
    private Api api;

    @Builder
    public Checks(Long responseTime, int response_code, Api api) {
        this.responseTime = responseTime;
        this.response_code = response_code;
        this.checked_at = LocalDateTime.now();
        this.api = api;
    }
}
