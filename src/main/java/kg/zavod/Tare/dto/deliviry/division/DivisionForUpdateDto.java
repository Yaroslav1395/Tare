package kg.zavod.Tare.dto.deliviry.division;

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
@Schema(description = "Класс необходим для редактирования территориального деления")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DivisionForUpdateDto {
    @Schema(description = "Id териториального деления")
    @NotNull(message = "Id териториального деления не может быть null")
    @Min(value = 1, message = "Id териториального деления не может быть меньше 1-го")
    private Integer id;
    @Schema(description = "Название")
    @NotNull(message = "Название территориального деления не может быть null")
    @NotEmpty(message = "Название территориального деления не может быть пустым")
    private String name;
}
