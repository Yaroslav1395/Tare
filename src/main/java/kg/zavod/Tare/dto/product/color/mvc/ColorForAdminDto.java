package kg.zavod.Tare.dto.product.color.mvc;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает цвет. Используется в админке MVC")
public class ColorForAdminDto {
    @Schema(description = "Id цвета")
    private Integer id;
    @Schema(description = "Hex код")
    private String hexCode;
    @Schema(description = "Название")
    private String name;
}
