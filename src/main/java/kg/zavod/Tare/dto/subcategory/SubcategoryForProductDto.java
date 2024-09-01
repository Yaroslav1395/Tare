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
@Schema(description = "Класс описывает подкатегорию для продукта")
public class SubcategoryForProductDto {
    @Schema(description = "Id подкатегории")
    private Integer id;
    @Schema(description = "Название")
    private String name;
    @Schema(description = "Id категории")
    private Integer categoryId;
}
