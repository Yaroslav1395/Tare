package kg.zavod.Tare.dto.notice;

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
@Schema(description = "Класс необходим для редактирования новости")
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoticeForUpdateDto {
    @Schema(description = "Id новости")
    @NotNull(message = "Id новости не может быть null")
    @Min(value = 1, message = "Id новости не может быть меньше 1-го")
    private Integer id;
    @Schema(description = "Заголовок новости")
    @NotNull(message = "Заголовок новости не может быть null")
    @NotEmpty(message = "Заголовок новости не может быть пустым")
    private String title;
    @Schema(description = "Описание новости")
    @NotNull(message = "Описание новости не может быть null")
    @NotEmpty(message = "Описание новости не может быть пустым")
    private String description;
    @Schema(description = "Картинка новости")
    @NotNull(message = "Картинка новости не может быть null")
    private MultipartFile noticeImage;
}
