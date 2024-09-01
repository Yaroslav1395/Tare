package kg.zavod.Tare.dto.deliviry.districtCapacityPrice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает цену для доставки определенного объема в определенный район")
public class DeliveryPriceDto {
    @Schema(description = "Id цены доставки")
    private Integer id;
    @Schema(description = "Цена доставки")
    private Integer price;
    @Schema(description = "Район доставки")
    private Integer districtId;
    @Schema(description = "Объем доставки")
    private Integer capacityId;
}
