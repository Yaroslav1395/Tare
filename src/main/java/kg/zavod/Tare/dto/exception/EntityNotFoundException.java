package kg.zavod.Tare.dto.exception;

public class EntityNotFoundException extends Exception {
    private final String message;

    public EntityNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
