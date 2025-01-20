package kg.zavod.Tare.dto.vacancy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает вакансию")
@JsonIgnoreProperties(ignoreUnknown = true)
public class VacancyForAdminDto {
    @Schema(description = "Id вакансии")
    private Integer id;
    @Schema(description = "Название вакансии")
    private String name;
    @Schema(description = "Описание вакансии")
    private String description;
    @Schema(description = "Предложение в вакансии")
    private String offer;
}
