package kg.zavod.Tare.controller.vacancy;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import kg.zavod.Tare.dto.ResponseDto;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.dto.vacancy.VacancyDto;
import kg.zavod.Tare.dto.vacancy.VacancyForSaveDto;
import kg.zavod.Tare.dto.vacancy.VacancyForUpdateDto;
import kg.zavod.Tare.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vacancy")
@Tag(name = "Вакансии", description = "Предоставляет возможность взаимодействовать с вакансиями")
@Validated
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;
    private static final Logger logger = LoggerFactory.getLogger(VacancyController.class);

    @Operation(summary = "Получение вакансии по идентификатору", description = "Позволит получить вакансию по идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping
    public ResponseEntity<ResponseDto<VacancyDto>> getVacancyById(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer vacancyId) throws EntityNotFoundException {
        logger.info("Получение вакансии по id");
        return ResponseEntity.ok(ResponseDto.buildResponse(vacancyService.getVacancyById(vacancyId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение всех вакансий", description = "Позволит получить все вакансии")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<VacancyDto>>> getAllVacancies() throws EntitiesNotFoundException {
        logger.info("Получение всех вакансий");
        return ResponseEntity.ok(ResponseDto.buildResponse(vacancyService.getAllVacancies(), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Создание вакансии", description = "Позволит создать вакансию")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PostMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<VacancyDto>> createVacancy(@ModelAttribute @Valid VacancyForSaveDto vacancyForSaveDto) {
        logger.info("Создание вакансии");
        return ResponseEntity.ok(ResponseDto.buildResponse(vacancyService.saveVacancy(vacancyForSaveDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Редактирование вакансии", description = "Позволит отредактировать вакансию")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PutMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<VacancyDto>> updateVacancy(@ModelAttribute @Valid VacancyForUpdateDto vacancyForUpdateDto) throws EntityNotFoundException {
        logger.info("Редактирование вакансии");
        return ResponseEntity.ok(ResponseDto.buildResponse(vacancyService.updateVacancy(vacancyForUpdateDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Удаление вакансии", description = "Позволит удалить вакансию")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<Boolean>> deleteVacancy(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer vacancyId) {
        logger.info("Удаление вакансии");
        return ResponseEntity.ok(ResponseDto.buildResponse(vacancyService.deleteVacancyById(vacancyId), ResponseState.SUCCESS,"Success"));
    }
}
