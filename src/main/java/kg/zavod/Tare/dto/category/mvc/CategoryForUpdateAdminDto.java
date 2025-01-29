package kg.zavod.Tare.dto.category.mvc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс необходим для редактирования категории. Используется в MVC")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryForUpdateAdminDto {
    @Schema(description = "Id категории")
    @NotNull(message = "Id категории не может быть null")
    @Min(value = 1, message = "Id категории не может быть меньше 1-го")
    private Integer id;
    @Schema(description = "Название")
    @NotNull(message = "Название категории не может быть null")
    @NotEmpty(message = "Название категории не может быть пустым")
    private String name;
    @Schema(description = "Картинка")
    @NotNull(message = "Картинка для категории не может быть null")
    private MultipartFile multipartFile;
}
