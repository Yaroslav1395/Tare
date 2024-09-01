package kg.zavod.Tare.dto.exception;

import lombok.Data;


public class EntitiesNotFoundException extends Exception {
    private final String message;

    public EntitiesNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
