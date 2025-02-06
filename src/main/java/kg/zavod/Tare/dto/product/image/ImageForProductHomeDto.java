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
@Schema(description = "Класс описывает картинку продукта. Используется на главной странице MVC")
public class ImageForProductHomeDto {
    @Schema(description = "Id картинки")
    private Integer id;
    @Schema(description = "Картинка продукта")
    private String productImage;
    @Schema(description = "Имя картинки продукта")
    private String productImageName;
    @Schema(description = "Тип картинки продукта")
    private String productImageType;
}
