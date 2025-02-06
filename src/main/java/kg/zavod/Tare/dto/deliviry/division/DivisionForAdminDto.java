package kg.zavod.Tare.dto.deliviry.division;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает территориальное деление")
public class DivisionForAdminDto {
    @Schema(description = "Id территориального деления")
    private Integer id;
    @Schema(description = "Название")
    private String name;
}
