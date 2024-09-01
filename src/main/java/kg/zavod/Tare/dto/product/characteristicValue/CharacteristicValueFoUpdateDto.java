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
@Schema(description = "Класс необходим для редактирования значения характеристики для продукта")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacteristicValueFoUpdateDto {
    @Schema(description = "Id значения характеристики")
    @NotNull(message = "Id значения характеристики не может быть null")
    @Min(value = 0, message = "Id значения характеристики не может быть отрицательным числом")
    private Integer characteristicValueId;
    @Schema(description = "Значение характеристики")
    @NotNull(message = "Значение характеристики не может быть null")
    @Min(value = 0, message = "Значение характеристики не может быть отрицательным числом")
    private Integer value;
}
