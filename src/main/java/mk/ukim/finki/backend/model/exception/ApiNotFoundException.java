package mk.ukim.finki.backend.model.exception;

public class ApiNotFoundException extends RuntimeException {
    public ApiNotFoundException(Long apiId) {
        super("API with id '%d' cannot be found.".formatted(apiId));
    }
}
