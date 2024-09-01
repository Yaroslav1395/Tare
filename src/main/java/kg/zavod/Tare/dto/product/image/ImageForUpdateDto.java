package kg.zavod.Tare.dto.product.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
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
@Schema(description = "Класс необходим для редактирования картинки продукта")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageForUpdateDto {
    @Schema(description = "Id картинки продукта")
    @NotNull(message = "Id картинки продукта не может быть null")
    @Min(value = 1, message = "Id картинки продукта не может быть меньше 1-го")
    private Integer id;
    @Schema(description = "Картинка продукта")
    @NotNull(message = "Картинка для продукта не может быть null")
    private MultipartFile productImage;
    @Schema(description = "Id цвета картинки")
    @NotNull(message = "Id цвета картинки не может быть null")
    @Min(value = 1, message = "Id цвета картинки не может быть меньше 1-го")
    private Integer colorId;
    @Schema(description = "Id продукта")
    @NotNull(message = "Id продукта не может быть null")
    @Min(value = 1, message = "Id продукта не может быть меньше 1-го")
    private Integer productId;
}
