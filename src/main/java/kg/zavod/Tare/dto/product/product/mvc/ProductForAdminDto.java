package kg.zavod.Tare.dto.product.product.mvc;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.zavod.Tare.dto.subcategory.SubcategoryForProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Schema(description = "Подкатегория продукта")
    private String subcategory;
}
