package kg.zavod.Tare.dto.exception;

import lombok.Getter;

@Getter
public class StaticDataException extends Exception {
    private final String message;

    public StaticDataException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
