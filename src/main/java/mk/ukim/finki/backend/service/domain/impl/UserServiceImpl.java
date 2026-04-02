package mk.ukim.finki.backend.service.domain.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.backend.model.domain.User;
import mk.ukim.finki.backend.model.exception.IncorrectPasswordException;
import mk.ukim.finki.backend.model.exception.UserNotFoundException;
import mk.ukim.finki.backend.repository.UserRepository;
import mk.ukim.finki.backend.service.domain.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(String username, String email, String password) {
        User user = User.builder()
                .username(username)
                .email(email)
                .hashed_password(passwordEncoder.encode(password))
                .build();

        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectPasswordException();
        }

        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return Optional.of(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
