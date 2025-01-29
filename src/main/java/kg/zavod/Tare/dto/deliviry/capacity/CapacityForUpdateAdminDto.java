package kg.zavod.Tare.dto.deliviry.capacity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс необходим для сохранения допустимого объема доставки")
public class CapacityForUpdateAdminDto {
    @Schema(description = "Id допустимого объема")
    @NotNull(message = "Id допустимого объема не может быть null")
    @Min(value = 1, message = "Id допустимого объема не может быть меньше 1-го")
    private Integer id;
    @Schema(description = "Лимит от")
    @NotNull(message = "Минимальный лимит не может быть null")
    @Min(value = 1, message = "Минимальный лимит не может быть меньше 1-го")
    private Integer capacityFrom;
    @Schema(description = "Лимит до")
    @NotNull(message = "Максимальный лимит не может быть null")
    @Min(value = 1, message = "Максимальный лимит не может быть меньше 1-го")
    private Integer capacityTo;
}
