package kg.zavod.Tare.dto.product.characteristic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Класс необходим для создания характеристики. Используется в админке MVC")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacteristicForSaveAdminDto {
    @Schema(description = "Название")
    @NotNull(message = "Название характеристики не может быть null")
    @NotEmpty(message = "Название характеристики не может быть null")
    private String name;
}
