package kg.zavod.Tare.dto.product.characteristicValue;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает значение характеристики для продукта. Используется в клиенте MVC")
public class CharacteristicValueForUserDto {
    @Schema(description = "Id характеристики")
    private String characteristicName;
    @Schema(description = "Значение характеристики")
    private Double value;
}
