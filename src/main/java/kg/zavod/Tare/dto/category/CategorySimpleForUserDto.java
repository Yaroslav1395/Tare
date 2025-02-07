package kg.zavod.Tare.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает категорию. Используется в клиенте MVC")
public class CategorySimpleForUserDto {
    @Schema(description = "Id категории")
    private Integer id;
    @Schema(description = "Название")
    private String name;
}
