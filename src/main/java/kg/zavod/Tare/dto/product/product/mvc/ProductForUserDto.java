package kg.zavod.Tare.dto.product.product.mvc;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.zavod.Tare.dto.product.characteristicValue.mvc.CharacteristicValueForUserDto;
import kg.zavod.Tare.dto.product.image.mvc.ImageForUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private Double price;
    @Schema(description = "Описание продукта")
    private String description;
    @Schema(description = "Короткое описание продукта")
    private String shortDescription;
    @Schema(description = "Артикул")
    private Integer idFromFactoryBd;
    private Integer categoryId;
    private String categoryName;
    private Integer subcategoryId;
    private String subcategoryName;
    @Schema(description = "Характеристики продукта")
    private List<CharacteristicValueForUserDto> productCharacteristics;
    @Schema(description = "Картинки продукта")
    private List<ImageForUserDto> images;
}
