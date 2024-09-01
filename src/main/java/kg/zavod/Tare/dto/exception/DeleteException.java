package kg.zavod.Tare.dto.exception;

import lombok.Getter;

@Getter
public class DeleteException extends Exception {
    private final String message;

    public DeleteException(String message) {
        super(message);
        this.message = message;
    }
}
