package kg.zavod.Tare.dto.deliviry.division;

import kg.zavod.Tare.domain.delivery.DivisionEntity;
import kg.zavod.Tare.dto.deliviry.district.DistrictForTableDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DivisionTableDto {
    private String divisionName;
    private List<DistrictForTableDto> districts;

    public static DivisionTableDto buildDivisionTableDto(DivisionEntity division, List<DistrictForTableDto> districts){
        return DivisionTableDto.builder()
                .divisionName(division.getName())
                .districts(districts)
                .build();
    }
}
