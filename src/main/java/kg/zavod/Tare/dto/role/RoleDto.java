package kg.zavod.Tare.dto.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает роли")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDto {
    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Название роли")
    @NotBlank
    private String role;
}
