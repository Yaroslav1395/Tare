package kg.zavod.Tare.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import kg.zavod.Tare.dto.ResponseDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.role.RoleDto;
import kg.zavod.Tare.dto.role.RoleForSaveDto;
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.service.user.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
@Tag(name = "Роли", description = "Предоставляет возможность взаимодействовать с ролями")
@Validated
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Operation(summary = "Получение роли", description = "Позволяет получить роль по идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/id")
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<RoleDto>> getRoleById(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id должно быть больше 0") Integer id) throws EntityNotFoundException {
        return ResponseEntity.ok(ResponseDto.buildResponse(roleService.getById(id), ResponseState.SUCCESS,"Success"));
    }
    @Operation(summary = "Получение всех ролей", description = "Позволяет получить все роли")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<List<RoleDto>>> getAllRole() throws EntitiesNotFoundException {
        return ResponseEntity.ok(ResponseDto.buildResponse(roleService.getAll(), ResponseState.SUCCESS,"Success"));
    }
    @Operation(summary = "Создание роли", description = "Позволяет создать новую роль")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PostMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<RoleDto>> createRole(@RequestBody @Valid RoleForSaveDto role) throws DuplicateEntityException {
        return ResponseEntity.ok(ResponseDto.buildResponse( roleService.saveRole(role), ResponseState.SUCCESS,"Success"));
    }
    @Operation(summary = "Редактирование роли", description = "Предоставляет возможность редактировать роль")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PutMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<RoleDto>> updateRole(@RequestBody @Valid RoleDto role) throws EntitiesNotFoundException, DuplicateEntityException {
        return ResponseEntity.ok(ResponseDto.buildResponse(roleService.updateRole(role), ResponseState.SUCCESS,"Success"));
    }
    @Operation(summary = "Удаление роли", description = "Предоставляет возможность удалить роль")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<Boolean>> deleteRole(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id должен быть больше 0") Integer id) {
        return ResponseEntity.ok(ResponseDto.buildResponse(roleService.deleteById(id), ResponseState.SUCCESS,"Success"));
    }
}
