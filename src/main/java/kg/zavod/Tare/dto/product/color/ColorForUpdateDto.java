package kg.zavod.Tare.dto.product.color;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс необходим для редактирования цвета")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ColorForUpdateDto {
    @Schema(description = "Id цвета")
    @NotNull(message = "Id цвета не может быть пустым")
    @Min(value = 1, message = "Id цвета не может быть меньше 1-го")
    private Integer id;
    @Schema(description = "Hex код")
    @NotNull(message = "Hex код не может быть null")
    @NotEmpty(message = "Hex код не может быть пустым")
    @Pattern(regexp = "^#?([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$", message = "Hex код должен быть в формате #RRGGBB или #RGB")
    private String hexCode;
    @Schema(description = "Название")
    @NotNull(message = "Название цвета не может быть null")
    @NotEmpty(message = "Название цвета не может быть пустым")
    private String name;
}
