package kg.zavod.Tare.dto.deliviry.district;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает район")
public class DistrictForAdminDto {
    @Schema(description = "Id района")
    private Integer id;
    @Schema(description = "Название")
    private String name;
    @Schema(description = "Название территориального деления к которому относится район")
    private String divisionName;
    @Schema(description = "Id территориального деления к которому относится район")
    private String divisionId;
}
