package kg.zavod.Tare.dto.deliviry.division;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.zavod.Tare.dto.deliviry.district.DistrictDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает территориальное деление")
public class DivisionDto {
    @Schema(description = "Id территориального деления")
    private Integer id;
    @Schema(description = "Название")
    private String name;
    @Schema(description = "Районы которые входят в территориальное деление")
    private List<DistrictDto> districts;
}
