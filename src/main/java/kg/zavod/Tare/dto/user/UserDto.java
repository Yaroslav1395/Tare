package kg.zavod.Tare.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import kg.zavod.Tare.dto.role.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс описывает минимальные данные пользователя")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    @Schema(description = "Идентификатор пользователя")
    private Integer id;
    @Schema(description = "Логин пользователя")
    private String username;
    @Schema(description = "Имя пользователя")
    private String name;
    @Schema(description = "Фамилия пользователя")
    private String surname;
    @Schema(description = "Отчество пользователя")
    private String patronymic;
    @Schema(description = "Номер телефона")
    private String phoneNumber;
    private List<RoleDto> roles;
}
