package kg.zavod.Tare.dto.deliviry.district;

import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceForUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistrictForTableDto {
    private Integer id;
    private String districtName;
    private Map<Integer, DeliveryPriceForUpdateDto> capacityPriceMap;
}
