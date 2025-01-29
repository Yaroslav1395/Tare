package kg.zavod.Tare.dto.subcategory.mvc;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.zavod.Tare.dto.product.product.mvc.ProductForHomeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает подкатегорию c продуктами. Используется совместно с классом категория. Создан для главной страницы MVC")
public class SubcategoryForHomeDto {
    @Schema(description = "Id подкатегории")
    private Integer id;
    @Schema(description = "Название подкатегории")
    private String name;
    @Schema(description = "Название подкатегории")
    private List<ProductForHomeDto> products;
}
