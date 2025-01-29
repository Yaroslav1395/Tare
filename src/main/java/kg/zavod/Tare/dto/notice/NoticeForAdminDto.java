package kg.zavod.Tare.dto.notice;

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
@Schema(description = "Класс описывает новость")
public class NoticeForAdminDto {
    @Schema(description = "Id новости")
    private Integer id;
    @Schema(description = "Заголовок новости")
    private String title;
    @Schema(description = "Описание новости")
    private String description;
    @Schema(description = "Время создания")
    private LocalDateTime createdTime;
    @Schema(description = "Время редактирования")
    private LocalDateTime updatedTime;
    @Schema(description = "Флаг активности новости")
    private Boolean isActive;
    @Schema(description = "Картинка новости")
    private String noticeImage;
    @Schema(description = "Имя картинки новости")
    private String noticeImageName;
    @Schema(description = "Тип картинки")
    private String imageType;
}
