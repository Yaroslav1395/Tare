package kg.zavod.Tare.dto.deliviry.capacity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает допустимый объем доставки")
public class CapacityForAdminDto {
    @Schema(description = "Id допустимого объема")
    private Integer id;
    @Schema(description = "Лимит от")
    private Integer capacityFrom;
    @Schema(description = "Лимит до")
    private Integer capacityTo;
}
