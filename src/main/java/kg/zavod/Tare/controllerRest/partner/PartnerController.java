package kg.zavod.Tare.controllerRest.partner;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import kg.zavod.Tare.controllerRest.vacancy.VacancyController;
import kg.zavod.Tare.dto.ResponseDto;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.partner.PartnerForAdminDto;
import kg.zavod.Tare.dto.partner.PartnerForSaveAdminDto;
import kg.zavod.Tare.dto.partner.PartnerForUpdateAdminDto;
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

//@RestController
@RequestMapping("partner")
@Tag(name = "Партнеры", description = "Предоставляет возможность взаимодействовать с партнерами")
@Validated
@RequiredArgsConstructor
public class PartnerController {
    private final PartnerService partnerService;
    private static final Logger logger = LoggerFactory.getLogger(VacancyController.class);

    @Operation(summary = "Получение партнера по идентификатору", description = "Позволит получить партнера по идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping
    public ResponseEntity<ResponseDto<PartnerForAdminDto>> getPartnerById(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer partnerId) throws EntityNotFoundException {
        logger.info("Получение партнера по id");
        return ResponseEntity.ok(ResponseDto.buildResponse(partnerService.getPartnerById(partnerId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение всех партнеров", description = "Позволит получить всех партнеров")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<PartnerForAdminDto>>> getAllPartners() throws EntitiesNotFoundException {
        logger.info("Получение всех партнеров");
        return ResponseEntity.ok(ResponseDto.buildResponse(partnerService.getAllPartners(), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение всех активных партнеров", description = "Позволит получить всех активных партнеров")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/all/active")
    public ResponseEntity<ResponseDto<List<PartnerForAdminDto>>> getAllActivePartners() throws EntitiesNotFoundException {
        logger.info("Получение всех активных партнеров");
        return ResponseEntity.ok(ResponseDto.buildResponse(partnerService.getAllActivePartners(), ResponseState.SUCCESS,"Success"));
    }


    @Operation(summary = "Создание партнера", description = "Позволит создать партнера")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<PartnerForAdminDto>> createPartner(@ModelAttribute @Valid PartnerForSaveAdminDto partnerForSaveAdminDto) throws EntityNotFoundException, IOException {
        logger.info("Создание партнера");
        /*return ResponseEntity.ok(ResponseDto.buildResponse(partnerService.savePartner(partnerForSaveAdminDto), ResponseState.SUCCESS,"Success"));*/
        return ResponseEntity.ok(ResponseDto.buildResponse(null, ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Редактирование партнера", description = "Позволит отредактировать партнера")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<PartnerForAdminDto>> updatePartner(@ModelAttribute @Valid PartnerForUpdateAdminDto partnerForUpdateAdminDto) throws EntityNotFoundException {
        logger.info("Редактирование партнера");
        /*return ResponseEntity.ok(ResponseDto.buildResponse(partnerService.updatePartner(partnerForUpdateAdminDto), ResponseState.SUCCESS,"Success"));*/
        return ResponseEntity.ok(ResponseDto.buildResponse(null, ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Удаление партнера", description = "Позволит удалить партнера")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<Boolean>> deletePartner(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer partnerId) {
        logger.info("Удаление партнера");
        return ResponseEntity.ok(ResponseDto.buildResponse(partnerService.deletePartnerById(partnerId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Изменение активности партнера", description = "Позволит изменить активность партнера")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    @PutMapping("/activity")
    public ResponseEntity<ResponseDto<Boolean>> changePartnerActivity(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer partnerId,
            @RequestParam @NotNull(message = "Id не может быть null") Boolean isActive) {
        logger.info("Изменение активности партнера");
        return ResponseEntity.ok(ResponseDto.buildResponse(partnerService.changePartnerActivityById(partnerId, isActive), ResponseState.SUCCESS,"Success"));
    }
}
