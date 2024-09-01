package kg.zavod.Tare.dto.product.characteristic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс необходим для создания характеристики")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacteristicForUpdateDto {
    @Schema(description = "Id характеристики")
    @NotNull(message = "Id цвета не может быть пустым")
    @Min(value = 1, message = "Id цвета не может быть меньше 1-го")
    private Integer id;
    @Schema(description = "Название")
    @NotNull(message = "Название характеристики не может быть null")
    @NotEmpty(message = "Название характеристики не может быть null")
    private String name;
}
