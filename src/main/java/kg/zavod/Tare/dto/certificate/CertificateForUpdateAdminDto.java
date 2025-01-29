package kg.zavod.Tare.dto.certificate;

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
@Schema(description = "Класс необходим для редактирования сертификата")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CertificateForUpdateAdminDto {
    @Schema(description = "Id сертификата")
    @NotNull(message = "Id сертификата не может быть null")
    @Min(value = 1, message = "Id сертификата не может быть меньше 1-го")
    private Integer id;
    @Schema(description = "Название сертификата")
    @NotNull(message = "Название сертификата не может быть null")
    @NotEmpty(message = "Название сертификата не может быть пустым")
    private String name;
    @Schema(description = "Описание сертификата")
    @NotNull(message = "Описание сертификата не может быть null")
    @NotEmpty(message = "Описание сертификата не может быть пустым")
    private String description;
    @Schema(description = "Картинка сертификата")
    @NotNull(message = "Картинка сертификата не может быть null")
    private MultipartFile certificateImage;
    @Schema(description = "Картинка сертификата на кыргызском")
    @NotNull(message = "Картинка сертификата на кыргызском не может быть null")
    private MultipartFile certificateImageKg;
}
