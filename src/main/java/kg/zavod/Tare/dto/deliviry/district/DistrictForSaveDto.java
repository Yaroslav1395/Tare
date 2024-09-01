package kg.zavod.Tare.dto.deliviry.district;

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
@Schema(description = "Класс необходим для создания района")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DistrictForSaveDto {
    @Schema(description = "Название")
    @NotNull(message = "Название района не может быть null")
    @NotEmpty(message = "Название района не может быть пустым")
    private String name;
    @Schema(description = "Id территориального деления к которому относится район")
    @NotNull(message = "Id территориального деления к которому относится район не может быть пустым")
    @Min(value = 1, message = "Id территориального деления к которому относится район не может быть меньше 1-го")
    private Integer divisionId;
}
