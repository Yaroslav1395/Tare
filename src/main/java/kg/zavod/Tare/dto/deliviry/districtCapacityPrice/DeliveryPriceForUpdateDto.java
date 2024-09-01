package kg.zavod.Tare.dto.deliviry.districtCapacityPrice;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс необходим для редактирования цены доставки определенного объема в определенный район")
public class DeliveryPriceForUpdateDto {
    @Schema(description = "Id цены доставки")
    @NotNull(message = "Id цены доставки не может быть null")
    @Min(value = 1, message = "Id цены доставки не может быть меньше 1-го")
    private Integer id;
    @Schema(description = "Цена доставки")
    //@NotNull(message = "Цена доставки не может быть null")
    private Integer price;
    /*@Schema(description = "Район доставки")
    @NotNull(message = "Id района доставки не может быть null")
    @Min(value = 1, message = "Id района доставки не может быть меньше 1-го")
    private Integer districtId;
    @Schema(description = "Объем доставки")
    @NotNull(message = "Id объема доставки не может быть null")
    @Min(value = 1, message = "Id объема доставки не может быть меньше 1-го")
    private Integer capacityId;*/
}
