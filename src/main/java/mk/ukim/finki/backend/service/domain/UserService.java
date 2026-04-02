package mk.ukim.finki.backend.service.domain;

import mk.ukim.finki.backend.model.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    User register(String username, String email, String password);

    User login(String username, String password);

    Optional<User> findByUsername(String username);
}
