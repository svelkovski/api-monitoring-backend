package mk.ukim.finki.backend.model.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("User with username '%s' cannot be found.".formatted(username));
    }
}
