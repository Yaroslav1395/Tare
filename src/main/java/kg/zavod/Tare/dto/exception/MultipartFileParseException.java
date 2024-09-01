package kg.zavod.Tare.dto.exception;

import lombok.Data;
import lombok.Getter;

@Getter
public class MultipartFileParseException extends RuntimeException {
    private final String message;

    public MultipartFileParseException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
