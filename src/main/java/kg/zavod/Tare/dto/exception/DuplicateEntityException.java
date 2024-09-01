package kg.zavod.Tare.dto.exception;

import lombok.Getter;

@Getter
public class DuplicateEntityException extends Exception {
    private final String message;
    public DuplicateEntityException(String message) {
        super(message);
        this.message = message;
    }
}
