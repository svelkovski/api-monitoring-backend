package mk.ukim.finki.backend.service.domain.impl;

import mk.ukim.finki.backend.model.domain.User;
import mk.ukim.finki.backend.model.exception.IncorrectPasswordException;
import mk.ukim.finki.backend.model.exception.UserNotFoundException;
import mk.ukim.finki.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldRegisterUser() {
        String rawPassword = "1234";
        String hashedPassword = "hashed";

        when(passwordEncoder.encode(rawPassword)).thenReturn(hashedPassword);

        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.register("test", "test@test.com", rawPassword);

        assertNotNull(result);
        assertEquals(hashedPassword, result.getHashed_password());
        assertEquals("test", result.getUsername());
    }

    @Test
    void shouldLoginSuccessfully() {
        User user = User.builder()
                .username("test")
                .hashed_password("hashed")
                .build();

        when(userRepository.findByUsername("test"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("1234", "hashed"))
                .thenReturn(true);

        User result = userService.login("test", "1234");

        assertNotNull(result);
        assertEquals("test", result.getUsername());
    }

    @Test
    void shouldThrowWhenPasswordIncorrect() {
        User user = User.builder()
                .username("test")
                .hashed_password("hashed")
                .build();

        when(userRepository.findByUsername("test"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("wrong", "hashed"))
                .thenReturn(false);

        assertThrows(IncorrectPasswordException.class, () -> userService.login("test", "wrong"));
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userRepository.findByUsername("test"))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.login("test", "test"));
    }
}
