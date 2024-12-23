package kg.zavod.Tare.controllerRest.product;

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
import kg.zavod.Tare.dto.product.color.ColorDto;
import kg.zavod.Tare.dto.product.color.ColorForSaveDto;
import kg.zavod.Tare.dto.product.color.ColorForUpdateDto;
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.service.product.ColorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@RequestMapping("color")
@Tag(name = "Цвет", description = "Предоставляет возможность взаимодействовать с цветом продукта")
@Validated
@RequiredArgsConstructor
public class ColorController {
    private final ColorService colorService;
    private static final Logger logger = LoggerFactory.getLogger(ColorController.class);

    @Operation(summary = "Получение цвета по идентификатору", description = "Позволит получить цвет по идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping
    public ResponseEntity<ResponseDto<ColorDto>> getColorById(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer colorId) throws EntityNotFoundException {
        logger.info("Получение цвета по id");
        return ResponseEntity.ok(ResponseDto.buildResponse(colorService.getColorById(colorId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение всех цветов", description = "Позволит получить все цвета")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<ColorDto>>> getAllColors() throws EntitiesNotFoundException {
        logger.info("Получение всех цветов");
        return ResponseEntity.ok(ResponseDto.buildResponse(colorService.getAllColors(), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Создание цвета", description = "Позволит создать цвет")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PostMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<ColorDto>> createColor(@RequestBody @Valid ColorForSaveDto colorForSaveDto) throws DuplicateEntityException {
        logger.info("Создание цвета");
        return ResponseEntity.ok(ResponseDto.buildResponse(colorService.saveColor(colorForSaveDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Редактирование цвета", description = "Позволит отредактировать цвет")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PutMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<ColorDto>> updateColor(@RequestBody @Valid ColorForUpdateDto colorForUpdateDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Редактирование цвета");
        return ResponseEntity.ok(ResponseDto.buildResponse(colorService.updateColor(colorForUpdateDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Удаление цвета", description = "Позволит удалить цвет")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<Boolean>> deleteColor(
            @RequestParam @NotNull(message = "Id цвета не может быть null")
            @Min(value = 1, message = "Id цвета не может быть меньше 1-го") Integer colorId) {
        logger.info("Удаление цвета");
        return ResponseEntity.ok(ResponseDto.buildResponse(colorService.deleteColorById(colorId), ResponseState.SUCCESS,"Success"));
    }
}
