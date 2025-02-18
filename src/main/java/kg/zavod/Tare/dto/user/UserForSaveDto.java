package kg.zavod.Tare.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс необходим для создания нового пользователя")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserForSaveDto {
    @NotNull(message = "Логин пользователя не может быть null")
    @Size(min = 3, max = 20, message = "Логин пользователя должен содержать от 3 до 20 символов")
    @Schema(description = "Логин пользователя")
    private String username;
    @NotNull(message = "Имя пользователя не может быть null")
    @Size(min = 3, max = 20, message = "Имя пользователя должен содержать от 3 до 20 символов")
    @Schema(description = "Имя пользователя")
    private String name;
    @NotNull(message = "Фамилия пользователя не может быть null")
    @Size(min = 3, max = 20, message = "Фамилия пользователя должен содержать от 3 до 20 символов")
    @Schema(description = "Фамилия пользователя")
    private String surname;
    @NotNull(message = "Отчество пользователя не может быть null")
    @Size(min = 3, max = 20, message = "Отчество пользователя должен содержать от 3 до 20 символов")
    @Schema(description = "Отчество пользователя")
    private String patronymic;
    @Pattern(regexp = "^996\\d{9}$", message = "Номер телефона пользователя должен соответствовать формату 996554601190")
    @Schema(description = "Номер телефона")
    private String phoneNumber;
    @NotNull(message = "Роли пользвоателя не могут быть null")
    @NotEmpty(message = "Роли пользователя не могут быть пустыми")
    @Schema(description = "Id ролей пользователя")
    private List<Integer> rolesId;
}
