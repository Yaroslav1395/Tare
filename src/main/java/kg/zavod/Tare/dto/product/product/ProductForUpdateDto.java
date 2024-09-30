package kg.zavod.Tare.dto.product.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueFoUpdateDto;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForSaveWithProductDto;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForUpdateWithProductDto;
import kg.zavod.Tare.dto.product.image.ImageForSaveWithProductDto;
import kg.zavod.Tare.dto.product.image.ImageForUpdateDto;
import kg.zavod.Tare.dto.product.image.ImageForUpdateWithProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс необходим  для редактирования продукта")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductForUpdateDto {
    @Schema(description = "Id продукта")
    @NotNull(message = "Id продукта не может быть null")
    @Min(value = 1, message = "Id продукта не может быть меньше 1-го")
    private Integer id;
    @Schema(description = "Название продукта")
    @NotNull(message = "Название продукта не может быть null")
    @NotEmpty(message = "Название не может быть пустым")
    private String name;
    @Schema(description = "Id продукта из базы завода")
    @NotNull(message = "Id продукта из базы завода не может быть null")
    @Min(value = 1, message = "Id продукта из базы завода не может быть меньше 1-го")
    private Integer idFromFactoryBd;
    @Schema(description = "Id подкатегории")
    @NotNull(message = "Id подкатегории не может быть null")
    @Min(value = 1, message = "Id подкатегории не может быть меньше 1-го")
    private Integer subcategoryId;
    @Schema(description = "Картинки продукта")
    private List<@Valid ImageForUpdateWithProductDto> images;
    @Schema(description = "Характеристики продукта")
    private List<@Valid CharacteristicValueForUpdateWithProductDto> characteristics;
}
