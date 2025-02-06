package kg.zavod.Tare.dto.product.characteristic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает характеристику продукта. Используется в админке MVC")
public class CharacteristicForAdminDto {
    @Schema(description = "Id характеристики")
    private Integer id;
    @Schema(description = "Название")
    private String name;
}
