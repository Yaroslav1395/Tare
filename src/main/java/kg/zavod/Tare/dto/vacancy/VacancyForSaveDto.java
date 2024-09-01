package kg.zavod.Tare.dto.vacancy;

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
@Schema(description = "Класс необходим для сохранения вакансии")
@JsonIgnoreProperties(ignoreUnknown = true)
public class VacancyForSaveDto {
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
