package kg.zavod.Tare.dto.subcategory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает подкатегорию")
public class SubcategoryDto {
    @Schema(description = "Id подкатегории")
    private Integer id;
    @Schema(description = "Название")
    private String name;
    @Schema(description = "Картинка подкатегории")
    private String subcategoryImage;
    @Schema(description = "имя картинки подкатегории")
    private String subcategoryImageName;
    @Schema(description = "Тип картинки")
    private String subcategoryImageType;
    @Schema(description = "Id категории")
    private Integer categoryId;
    @Schema(description = "Id продуктов входящих в подкатегорию")
    private List<Integer> productsIds;
}
