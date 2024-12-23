package kg.zavod.Tare.controllerRest.delivery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import kg.zavod.Tare.dto.ResponseDto;
import kg.zavod.Tare.dto.deliviry.district.DistrictDto;
import kg.zavod.Tare.dto.deliviry.district.DistrictForSaveDto;
import kg.zavod.Tare.dto.deliviry.district.DistrictForUpdateDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.service.delivery.DistrictService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@RequestMapping("district")
@Tag(name = "Районы", description = "Предоставляет возможность взаимодействовать с районом")
@Validated
@RequiredArgsConstructor
public class DistrictController {
    private final DistrictService districtService;
    private static final Logger logger = LoggerFactory.getLogger(DistrictController.class);

    @Operation(summary = "Получение района по идентификатору", description = "Позволит получить района по идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping
    public ResponseEntity<ResponseDto<DistrictDto>> getDistrictById(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer districtId) throws EntityNotFoundException {
        logger.info("Получение района по id");
        return ResponseEntity.ok(ResponseDto.buildResponse(districtService.getDistrictById(districtId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение всех районов", description = "Позволит получить все районы")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<DistrictDto>>> getAllDistricts() throws EntitiesNotFoundException {
        logger.info("Получение всех районов");
        return ResponseEntity.ok(ResponseDto.buildResponse(districtService.getAllDistricts(), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Создание района", description = "Позволит создать район")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PostMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<DistrictDto>> createDistrict(@ModelAttribute @Valid DistrictForSaveDto districtForSaveDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Создание района");
        return ResponseEntity.ok(ResponseDto.buildResponse(districtService.saveDistrict(districtForSaveDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Редактирование района", description = "Позволит отредактировать район")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PutMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<DistrictDto>> updateDistrict(@ModelAttribute @Valid DistrictForUpdateDto districtForUpdateDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Редактирование района");
        return ResponseEntity.ok(ResponseDto.buildResponse(districtService.updateDistrict(districtForUpdateDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Удаление района", description = "Позволит удалить район")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<Boolean>> deleteDistrict(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer districtId) {
        logger.info("Удаление района");
        return ResponseEntity.ok(ResponseDto.buildResponse(districtService.deleteDistrictById(districtId), ResponseState.SUCCESS,"Success"));
    }
}
