package kg.zavod.Tare.dto.subcategory.mvc;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает подкатегорию для клиентской страницы подкатегории")
public class SubcategoryForUserDto {
    @Schema(description = "Id подкатегории")
    private Integer id;
    @Schema(description = "Название подкатегории")
    private String name;
    @Schema(description = "Картинка подкатегории")
    private String subcategoryImage;
    @Schema(description = "Id категории")
    private Integer categoryId;
    @Schema(description = "Название категории")
    private String categoryName;
}
