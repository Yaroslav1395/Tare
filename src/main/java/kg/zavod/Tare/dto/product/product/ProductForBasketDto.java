package kg.zavod.Tare.dto.product.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает продукт для корзины. Используется клиентом MVC")
public class ProductForBasketDto {
    @Schema(description = "Продукт")
    private ProductForUserDto product;
    @Schema(description = "Количество")
    private Integer count;
}
