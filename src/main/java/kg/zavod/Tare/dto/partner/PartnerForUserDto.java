package kg.zavod.Tare.dto.partner;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает партнера для клиента")
public class PartnerForUserDto {
    @Schema(description = "Id партнера")
    private Integer id;
    @Schema(description = "Название партнера")
    private String name;
    @Schema(description = "Описание партнерства")
    private String description;
    @Schema(description = "Логотип партнера")
    private String logoImage;
    @Schema(description = "Имя логотип партнера")
    private String logoImageName;
    @Schema(description = "Картинка продукции партнера")
    private String productImage;
    @Schema(description = "Имя картинка продукции партнера")
    private String productImageName;
}
