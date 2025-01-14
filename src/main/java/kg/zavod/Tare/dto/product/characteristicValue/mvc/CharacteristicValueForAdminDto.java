package kg.zavod.Tare.dto.product.characteristicValue.mvc;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает значение характеристики для продукта. Используется в админке MVC")
public class CharacteristicValueForAdminDto {
    @Schema(description = "Id значения характеристики")
    private Integer id;
    @Schema(description = "Id характеристики")
    private Integer characteristicId;
    @Schema(description = "Значение характеристики")
    private Integer value;
}
