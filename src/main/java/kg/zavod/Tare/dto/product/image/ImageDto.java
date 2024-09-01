package kg.zavod.Tare.dto.product.image;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.zavod.Tare.dto.product.color.ColorDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает картинку продукта")
public class ImageDto {
    @Schema(description = "Id картинки")
    private Integer id;
    @Schema(description = "Картинка продукта")
    private String productImage;
    @Schema(description = "Тип картинки продукта")
    private String productImageType;
    @Schema(description = "Цвет картинки")
    private ColorDto color;
    @Schema(description = "Id продукта")
    private Integer productId;
}
