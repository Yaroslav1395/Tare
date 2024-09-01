package kg.zavod.Tare.dto.deliviry;

import kg.zavod.Tare.dto.deliviry.capacity.CapacityDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionTableDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryTable {
    private List<CapacityDto> capacities;
    private List<DivisionTableDto> divisions;

    public static DeliveryTable buildDeliveryTable(List<DivisionTableDto> divisions, List<CapacityDto> capacities){
        return DeliveryTable.builder()
                .divisions(divisions)
                .capacities(capacities)
                .build();
    }
}
