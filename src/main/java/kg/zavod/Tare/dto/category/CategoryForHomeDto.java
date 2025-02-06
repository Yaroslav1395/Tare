package kg.zavod.Tare.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.zavod.Tare.dto.subcategory.SubcategoryForHomeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает категорию. Используется на главной странице MVC")
public class CategoryForHomeDto {
    @Schema(description = "Id категории")
    private Integer id;
    @Schema(description = "Название")
    private String name;
    @Schema(description = "Картинка категории")
    private String categoryImage;
    @Schema(description = "имя картинки категории")
    private String categoryImageName;
    @Schema(description = "Тип картинки")
    private String imageType;
    @Schema(description = "Список подкатегорий")
    private List<SubcategoryForHomeDto> subcategories;
}
