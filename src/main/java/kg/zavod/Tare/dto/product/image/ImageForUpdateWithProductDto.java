package kg.zavod.Tare.dto.product.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import kg.zavod.Tare.dto.product.color.ColorDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс необходим для редактирования картинки продукта вмести с продуктом")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageForUpdateWithProductDto {
    @Schema(description = "Id картинки продукта")
    private Integer id;
    @Schema(description = "Картинка продукта")
    @NotNull(message = "Картинка для продукта не может быть null")
    private MultipartFile productImage;
    @Schema(description = "Id цвета картинки")
    @NotNull(message = "Id цвета картинки не может быть null")
    @Min(value = 1, message = "Id цвета картинки не может быть меньше 1-го")
    private ColorDto color;
}
