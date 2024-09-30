package kg.zavod.Tare.dto.product.characteristicValue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Schema(description = "Класс необходим для редактирования значения характеристики вместе с продуктом")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacteristicValueForUpdateWithProductDto {
    @Schema(description = "Id сохраненного значения характеристики")
    private Integer id;
    @Schema(description = "Id характеристики")
    @NotNull(message = "Id характеристики не может быть null")
    @Min(value = 1, message = "Id характеристики не может быть меньше 1-го")
    private Integer characteristicId;
    @Schema(description = "Значение характеристики")
    @NotNull(message = "Значение характеристики не может быть null")
    @Min(value = 0, message = "Значение характеристики не может быть отрицательным числом")
    private Integer value;
}
