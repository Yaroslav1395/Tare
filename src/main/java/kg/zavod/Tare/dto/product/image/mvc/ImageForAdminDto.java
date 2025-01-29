package kg.zavod.Tare.dto.product.image.mvc;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает картинку продукта")
public class ImageForAdminDto {
    @Schema(description = "Id картинки")
    private Integer id;
    @Schema(description = "Картинка продукта")
    private String productImage;
    @Schema(description = "Цвет картинки")
    private Integer colorId;
}
