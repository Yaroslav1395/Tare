package kg.zavod.Tare.dto.certificate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает сертификат для клиента")
public class CertificateForUserDto {
    @Schema(description = "Название сертификата")
    private String name;
    @Schema(description = "Описание сертификата")
    private String description;
    @Schema(description = "Картинка сертификата")
    private String certificateImage;
    @Schema(description = "Имя картинки сертификата")
    private String certificateImageName;
    @Schema(description = "Картинка сертификата на кыргызском")
    private String certificateImageKg;
    @Schema(description = "Имя картинки сертификата на кыргызском")
    private String certificateImageNameKg;
}
