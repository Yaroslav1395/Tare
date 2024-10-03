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
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.dto.user.UserForSaveDto;
import kg.zavod.Tare.dto.user.UserForUpdateDto;
import kg.zavod.Tare.dto.user.UserDto;
import kg.zavod.Tare.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("user")
@Tag(name = "Пользователь", description = "Предоставляет возможность взаимодействовать с пользователями")
@Validated
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Operation(summary = "Получение пользователя по идентификатору", description = "Позволит получить пользователя по идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<UserDto>> getUserById(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer userId) throws EntityNotFoundException {
        logger.info("Получение пользователя по id");
        return ResponseEntity.ok(ResponseDto.buildResponse(userService.getUserById(userId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение всех пользователей", description = "Позволит получить всех пользователей")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<List<UserDto>>> getAllUsers() throws EntitiesNotFoundException {
        logger.info("Получение всех пользователей");
        return ResponseEntity.ok(ResponseDto.buildResponse(userService.getAllUsers(), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Создание пользователя", description = "Позволит создать пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PostMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<UserDto>> createUser(@RequestBody @Valid UserForSaveDto userForSaveDto) throws DuplicateEntityException {
        logger.info("Создание пользователя");
        return ResponseEntity.ok(ResponseDto.buildResponse(userService.createUser(userForSaveDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Редактирование пользователя", description = "Позволит отредактировать пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PutMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<UserDto>> updateUser(@RequestBody @Valid UserForUpdateDto userForUpdateDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Редактирование пользователя");
        return ResponseEntity.ok(ResponseDto.buildResponse(userService.updateUser(userForUpdateDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Удаление пользователя", description = "Позволит удалить пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<Boolean>> deleteUser(@RequestParam @NotNull @Min(1) Integer userId) {
        logger.info("Удаление пользователя");
        return ResponseEntity.ok(ResponseDto.buildResponse(userService.deleteById(userId), ResponseState.SUCCESS,"Success"));
    }
}
