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
@Schema(description = "Класс описывает подкатегорию. Используется совместно с классом категория")
public class SubcategorySimpleDto {
    @Schema(description = "Id подкатегории")
    private Integer id;
    @Schema(description = "Название подкатегории")
    private String name;
}
