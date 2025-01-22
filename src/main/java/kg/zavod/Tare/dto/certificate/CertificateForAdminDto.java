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
@Schema(description = "Класс описывает сертификат")
public class CertificateForAdminDto {
    @Schema(description = "Id сертификата")
    private Integer id;
    @Schema(description = "Название сертификата")
    private String name;
    @Schema(description = "Описание сертификата")
    private String description;
    @Schema(description = "Картинка сертификата")
    private String certificateImage;
    @Schema(description = "Название картинка сертификата")
    private String certificateImageName;
    @Schema(description = "Тип картинки сертификата")
    private String certificateImageType;
    @Schema(description = "Картинка сертификата на кыргызском")
    private String certificateImageKg;
    @Schema(description = "Название картинка сертификата на кыргызском")
    private String certificateImageNameKg;
    @Schema(description = "Тип картинки сертификата на кыргызском")
    private String certificateImageTypeKg;
}
