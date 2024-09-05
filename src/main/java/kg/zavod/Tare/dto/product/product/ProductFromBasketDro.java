package kg.zavod.Tare.dto.product.product;

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
@Schema(description = "Класс описывает продукт из корзины")
public class ProductFromBasketDro {
    @Schema(description = "Id продукта")
    @NotNull(message = "Id продукта не может быть null")
    @Min(value = 1, message = "Id продукта не может быть меньше 1-го")
    private Integer id;
    @Schema(description = "Количество продукта")
    @NotNull(message = "Количество продукта не может быть null")
    @Min(value = 1, message = "Количество продукта не может быть меньше 1-го")
    private Integer amount;
}
