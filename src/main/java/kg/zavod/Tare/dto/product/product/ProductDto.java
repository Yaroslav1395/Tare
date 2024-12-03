package kg.zavod.Tare.dto.product.product;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueDto;
import kg.zavod.Tare.dto.product.image.ImageDto;
import kg.zavod.Tare.dto.subcategory.SubcategoryDto;
import kg.zavod.Tare.dto.subcategory.SubcategoryForProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает продукт")
public class ProductDto {
    @Schema(description = "Id продукта")
    private Integer id;
    @Schema(description = "Id продукта из базы завода")
    private Integer idFromFactoryBd;
    @Schema(description = "Название продукта")
    private String name;
    @Schema(description = "Подкатегория продукта")
    private SubcategoryForProductDto subcategory;
    @Schema(description = "Значения характеристик продукта")
    private List<CharacteristicValueDto> productCharacteristics;
    @Schema(description = "Картинки продукта")
    private List<ImageDto> images;
    @Schema(description = "Цена продукта")
    private Integer price;
}
