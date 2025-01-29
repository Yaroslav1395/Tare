package kg.zavod.Tare.dto.partner;

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
@Schema(description = "Класс необходим для создания партнера")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PartnerForUpdateAdminDto {
    @Schema(description = "Id партнера")
    @NotNull(message = "Id партнера не может быть null")
    @Min(value = 1, message = "Id партнера не может быть меньше 1-го")
    private Integer id;
    @Schema(description = "Название партнера")
    @NotNull(message = "Название партнера не может быть null")
    @NotEmpty(message = "Название партнера не может быть пустым")
    private String name;
    @Schema(description = "Описание партнерства")
    @NotNull(message = "Описание партнерства не может быть null")
    @NotEmpty(message = "Описание партнерства не может быть пустым")
    private String description;
    @Schema(description = "Логотип партнера")
    @NotNull(message = "Логотип партнера не может быть null")
    private MultipartFile logoImage;
    @Schema(description = "Картинка продукции партнера")
    @NotNull(message = "Картинка продукции партнера не может быть null")
    private MultipartFile productImage;
}
