package kg.zavod.Tare.dto.vacancy;

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
@Schema(description = "Класс необходим для редактирования вакансии")
@JsonIgnoreProperties(ignoreUnknown = true)
public class VacancyForUpdateAdminDto {
    @Schema(description = "Id вакансии")
    @NotNull(message = "Id вакансии не может быть null")
    @Min(value = 1, message = "Id вакансии не может быть меньше 1-го")
    private Integer id;
    @Schema(description = "Название вакансии")
    @NotNull(message = "Название вакансии не может быть null")
    @NotEmpty(message = "Название вакансии не может быть пустым")
    private String name;
    @Schema(description = "Описание вакансии")
    @NotNull(message = "Описание вакансии не может быть null")
    @NotEmpty(message = "Описание вакансии не может быть пустым")
    private String description;
    @Schema(description = "Предложение в вакансии")
    @NotNull(message = "Предложение в вакансии не может быть null")
    @NotEmpty(message = "Предложение в вакансии не может быть пустым")
    private String offer;
}
