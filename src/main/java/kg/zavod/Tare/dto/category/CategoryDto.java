package kg.zavod.Tare.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.zavod.Tare.dto.subcategory.SubcategorySimpleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает категорию")
public class CategoryDto {
    @Schema(description = "Id категории")
    private Integer id;
    @Schema(description = "Название")
    private String name;
    @Schema(description = "Картинка")
    private String categoryImage;
    @Schema(description = "Тип картинки")
    private String imageType;
    @Schema(description = "Список подкатегорий")
    private List<SubcategorySimpleDto> subcategories;
}
