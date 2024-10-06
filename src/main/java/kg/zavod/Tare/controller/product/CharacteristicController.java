package kg.zavod.Tare.controller.product;

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
import kg.zavod.Tare.dto.exception.StaticDataException;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicDto;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForSaveDto;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForUpdateDto;
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.service.product.CharacteristicService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("characteristic")
@Tag(name = "Характеристики продукта", description = "Предоставляет возможность взаимодействовать с характеристиками продукта")
@Validated
@RequiredArgsConstructor
public class CharacteristicController {
    private final CharacteristicService characteristicService;
    private static final Logger logger = LoggerFactory.getLogger(CharacteristicController.class);

    @Operation(summary = "Получение характеристики по идентификатору", description = "Позволит получить характеристику по идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping
    public ResponseEntity<ResponseDto<CharacteristicDto>> getCharacteristicById(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer characteristicId) throws EntityNotFoundException {
        logger.info("Получение характеристики по id");
        return ResponseEntity.ok(ResponseDto.buildResponse(characteristicService.getCharacteristicById(characteristicId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение всех характеристик", description = "Позволит получить все характеристики")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<CharacteristicDto>>> getAllCharacteristics() throws EntitiesNotFoundException {
        logger.info("Получение всех характеристик");
        return ResponseEntity.ok(ResponseDto.buildResponse(characteristicService.getAllCharacteristics(), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Создание характеристики", description = "Позволит создать характеристику")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PostMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<CharacteristicDto>> createCharacteristic(@RequestBody @Valid CharacteristicForSaveDto characteristicForSaveDto) throws DuplicateEntityException {
        logger.info("Создание характеристики");
        return ResponseEntity.ok(ResponseDto.buildResponse(characteristicService.saveCharacteristic(characteristicForSaveDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Редактирование характеристики", description = "Позволит отредактировать характеристику")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PutMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<CharacteristicDto>> updateCharacteristic(@RequestBody @Valid CharacteristicForUpdateDto characteristicForUpdateDto) throws EntityNotFoundException, DuplicateEntityException, StaticDataException {
        logger.info("Редактирование характеристики");
        return ResponseEntity.ok(ResponseDto.buildResponse(characteristicService.updateCharacteristic(characteristicForUpdateDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Удаление характеристики", description = "Позволит удалить характеристику")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<Boolean>> deleteCharacteristic(
            @RequestParam @NotNull(message = "Id цвета не может быть null")
            @Min(value = 1, message = "Id цвета не может быть меньше 1-го") Integer characteristicId) throws StaticDataException {
        logger.info("Удаление характеристики");
        return ResponseEntity.ok(ResponseDto.buildResponse(characteristicService.deleteCharacteristicById(characteristicId), ResponseState.SUCCESS,"Success"));
    }
}
