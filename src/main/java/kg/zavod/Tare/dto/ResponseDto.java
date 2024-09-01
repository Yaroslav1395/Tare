package kg.zavod.Tare.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.zavod.Tare.dto.state.ResponseState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает ответ от сервера")
public class ResponseDto <T> {
    @Schema(description = "Статус ответа")
    private ResponseState state;
    @Schema(description = "Сообщение об ошибке")
    private String message;
    @Schema(description = "Полезная нагрузка")
    private T data;

    public static <T> ResponseDto<T> buildResponse(ResponseState responseState, String message) {
        return new ResponseDto<>(responseState, message, null);
    }
    public static <T> ResponseDto<T> buildResponse(T data, ResponseState responseState, String message) {
        return new ResponseDto<>(responseState, message, data);
    }
}
