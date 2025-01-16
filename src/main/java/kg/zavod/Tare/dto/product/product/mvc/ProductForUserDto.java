package kg.zavod.Tare.dto.product.product.mvc;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает продукт. Используется на странице продуктов MVC")
public class ProductForUserDto {
    @Schema(description = "Id продукта")
    private Integer id;
    @Schema(description = "Название продукта")
    private String name;
    @Schema(description = "Цена продукта")
    private Integer price;
}
