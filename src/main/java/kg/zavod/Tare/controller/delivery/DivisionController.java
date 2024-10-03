package kg.zavod.Tare.controller.delivery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import kg.zavod.Tare.dto.ResponseDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionForSaveDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionForUpdateDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.service.delivery.DivisionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("division")
@Tag(name = "Территориальное деление", description = "Предоставляет возможность взаимодействовать с территориальным делением")
@Validated
@RequiredArgsConstructor
public class DivisionController {
    private final DivisionService divisionService;
    private static final Logger logger = LoggerFactory.getLogger(DivisionController.class);

    @Operation(summary = "Получение территориального деления по идентификатору", description = "Позволит получить территориальное деление по идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping
    public ResponseEntity<ResponseDto<DivisionDto>> getDivisionById(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer divisionId) throws EntityNotFoundException {
        logger.info("Получение территориального деления по id");
        return ResponseEntity.ok(ResponseDto.buildResponse(divisionService.getDivisionById(divisionId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение всех территориальных делений", description = "Позволит получить все территориальные деления")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<DivisionDto>>> getAllDivisions() throws EntitiesNotFoundException {
        logger.info("Получение всех территориальных делений");
        return ResponseEntity.ok(ResponseDto.buildResponse(divisionService.getAllDivisions(), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Создание территориального деления", description = "Позволит создать территориальное деление")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PostMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<DivisionDto>> createDivision(@ModelAttribute @Valid DivisionForSaveDto divisionForSaveDto) throws DuplicateEntityException {
        logger.info("Создание территориального деления");
        return ResponseEntity.ok(ResponseDto.buildResponse(divisionService.saveDivision(divisionForSaveDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Редактирование территориального деления", description = "Позволит отредактировать территориальное деление")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PutMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<DivisionDto>> updateDivision(@ModelAttribute @Valid DivisionForUpdateDto divisionForUpdateDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Редактирование территориального деления");
        return ResponseEntity.ok(ResponseDto.buildResponse(divisionService.updateDivision(divisionForUpdateDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Удаление территориального деления", description = "Позволит удалить территориальное деление")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<Boolean>> deleteDivision(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer divisionId) {
        logger.info("Удаление территориального деления");
        return ResponseEntity.ok(ResponseDto.buildResponse(divisionService.deleteDivisionById(divisionId), ResponseState.SUCCESS,"Success"));
    }
}
