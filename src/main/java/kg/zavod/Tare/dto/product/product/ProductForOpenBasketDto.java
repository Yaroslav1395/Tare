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
@Schema(description = "Класс описывает продукт. Используется при отправке запроса на получение корзины для клиента MVC")
public class ProductForOpenBasketDto {
    private String id;
    private Integer count;
}
