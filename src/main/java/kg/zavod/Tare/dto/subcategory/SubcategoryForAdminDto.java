package kg.zavod.Tare.dto.subcategory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс необходим для отображения подкатегорий. Используется в админке MVC")
public class SubcategoryForAdminDto {
    @Schema(description = "Id подкатегории")
    private Integer id;
    @Schema(description = "Название")
    private String name;
    @Schema(description = "Картинка подкатегории")
    private String subcategoryImage;
    @Schema(description = "Имя картинки подкатегории")
    private String subcategoryImageName;
    @Schema(description = "Тип картинки")
    private String subcategoryImageType;
    @Schema(description = "Название категории")
    private String categoryName;
}
