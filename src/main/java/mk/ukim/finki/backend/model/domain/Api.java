package mk.ukim.finki.backend.model.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.backend.model.enums.HttpMethod;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "api")
public class Api extends BaseAuditableEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "http_method")
    private HttpMethod method;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "api")
    private List<Checks> checks = new ArrayList<>();

    public Api(String name, String url, HttpMethod method, User user) {
        this.name = name;
        this.url = url;
        this.method = method;
        this.status = "UNKNOWN";
        this.user = user;
    }
}
