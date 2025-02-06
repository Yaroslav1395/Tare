package kg.zavod.Tare.dto.product.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForUpdateAdminDto;
import kg.zavod.Tare.dto.product.image.ImageForUpdateAdminDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс необходим  для редактирования продукта")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductForUpdateAdminDto {
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
    @Schema(description = "Описание продукта")
    @NotNull(message = "Описание продукта не может быть null")
    @NotEmpty(message = "Описание продукта не может быть пустым")
    private String description;
    @Schema(description = "Id подкатегории")
    @NotNull(message = "Id подкатегории не может быть null")
    @Min(value = 1, message = "Id подкатегории не может быть меньше 1-го")
    private Integer subcategoryId;
    @Schema(description = "Значения характеристик продукта")
    @NotEmpty(message = "Продукт должен иметь хотя бы одну характеристику")
    private List<@Valid CharacteristicValueForUpdateAdminDto> characteristicsValue;
    @Schema(description = "Картинки продукта")
    @NotEmpty(message = "Продукт должен иметь хотя бы одну картинку")
    private List<@Valid ImageForUpdateAdminDto> images;

    public ProductForUpdateAdminDto(int index) {
        List<ImageForUpdateAdminDto> imageList = new ArrayList<>();
        for (int i = 0; i < index; i++) {
            imageList.add(new ImageForUpdateAdminDto());
        }
        this.images = imageList;
    }
}
