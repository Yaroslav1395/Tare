package kg.zavod.Tare.controllerRest.delivery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import kg.zavod.Tare.dto.ResponseDto;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForAdminDto;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForSaveAdminDto;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForUpdateAdminDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.service.delivery.CapacityService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@RequestMapping("capacity")
@Tag(name = "Допустимые объемы доставки", description = "Предоставляет возможность взаимодействовать с допустимыми объемами доставки")
@Validated
@RequiredArgsConstructor
public class CapacityController {
    private final CapacityService capacityService;
    private static final Logger logger = LoggerFactory.getLogger(DistrictController.class);

    @Operation(summary = "Получение допустимого объема по идентификатору", description = "Позволит получить допустимый объем по идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping
    public ResponseEntity<ResponseDto<CapacityForAdminDto>> getCapacityById(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer districtId) throws EntityNotFoundException {
        logger.info("Получение допустимого объема по id");
        return ResponseEntity.ok(ResponseDto.buildResponse(capacityService.getCapacityById(districtId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение всех допустимых объемов", description = "Позволит получить все допустимые объемы")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<CapacityForAdminDto>>> getAllCapacities() throws EntitiesNotFoundException {
        logger.info("Получение всех допустимых объемов");
        return ResponseEntity.ok(ResponseDto.buildResponse(capacityService.getAllCapacities(), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Создание допустимого объема", description = "Позволит создать допустимый объем")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PostMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<CapacityForAdminDto>> createCapacity(@ModelAttribute @Valid CapacityForSaveAdminDto capacityForSaveAdminDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Создание допустимого объема");
        /*return ResponseEntity.ok(ResponseDto.buildResponse(capacityService.saveCapacity(capacityForSaveAdminDto), ResponseState.SUCCESS,"Success"));*/
        return ResponseEntity.ok(ResponseDto.buildResponse(null, ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Редактирование допустимого объема", description = "Позволит отредактировать допустимый объем")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PutMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<CapacityForAdminDto>> updateCapacity(@ModelAttribute @Valid CapacityForUpdateAdminDto capacityForUpdateAdminDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Редактирование допустимого объема");
        return ResponseEntity.ok(ResponseDto.buildResponse(null, ResponseState.SUCCESS,"Success"));
        /*return ResponseEntity.ok(ResponseDto.buildResponse(capacityService.updateCapacity(capacityForUpdateAdminDto), ResponseState.SUCCESS,"Success"));*/
    }

    @Operation(summary = "Удаление допустимого объема", description = "Позволит удалить допустимый объем")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<Boolean>> deleteCapacity(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer districtId) {
        logger.info("Удаление допустимого объема");
        return ResponseEntity.ok(ResponseDto.buildResponse(null, ResponseState.SUCCESS,"Success"));
        /*return ResponseEntity.ok(ResponseDto.buildResponse(capacityService.deleteCapacityById(districtId), ResponseState.SUCCESS,"Success"));*/
    }
}
