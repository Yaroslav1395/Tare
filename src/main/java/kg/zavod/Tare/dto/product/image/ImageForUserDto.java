package kg.zavod.Tare.dto.product.image;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает картинку продукта. Используется в клиенте MVC")
public class ImageForUserDto {
    @Schema(description = "Картинка продукта")
    private String productImage;
    @Schema(description = "Название картинки")
    private String productImageName;
    @Schema(description = "Цвет картинки")
    private String color;
}
