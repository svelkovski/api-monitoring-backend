package mk.ukim.finki.backend.service.domain;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails);

    String extractUsername(String token);

    boolean isExpired(String token);
}
