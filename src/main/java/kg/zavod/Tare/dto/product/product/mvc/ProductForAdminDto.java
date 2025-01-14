package kg.zavod.Tare.dto.product.product.mvc;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.zavod.Tare.dto.product.characteristicValue.mvc.CharacteristicValueForAdminDto;
import kg.zavod.Tare.dto.product.image.mvc.ImageForAdminDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает продукт. Используется в админке MVC")
public class ProductForAdminDto {
    @Schema(description = "Id продукта")
    private Integer id;
    @Schema(description = "Id продукта из базы завода")
    private Integer idFromFactoryBd;
    @Schema(description = "Название продукта")
    private String name;
    @Schema(description = "Описание")
    private String description;
    @Schema(description = "Подкатегория продукта")
    private String subcategory;
    @Schema(description = "Характеристики продукта")
    private List<CharacteristicValueForAdminDto> characteristics;
    @Schema(description = "Картинки продукта")
    private List<ImageForAdminDto> images;
}
