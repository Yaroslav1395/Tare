package kg.zavod.Tare.dto.product.product.mvc;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.zavod.Tare.dto.product.image.mvc.ImageForProductHomeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает продукт. Используется на главной странице MVC")
public class ProductForHomeDto {
    @Schema(description = "Id продукта")
    private Integer id;
    @Schema(description = "Название продукта")
    private String name;
    @Schema(description = "Картинки продукта")
    private ImageForProductHomeDto image;
    @Schema(description = "Цена продукта")
    private Integer price;
}
