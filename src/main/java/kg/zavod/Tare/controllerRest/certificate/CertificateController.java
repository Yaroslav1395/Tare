package kg.zavod.Tare.controllerRest.certificate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import kg.zavod.Tare.dto.ResponseDto;
import kg.zavod.Tare.dto.certificate.CertificateForAdminDto;
import kg.zavod.Tare.dto.certificate.CertificateForSaveAdminDto;
import kg.zavod.Tare.dto.certificate.CertificateForUpdateAdminDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@RequestMapping("certificate")
@Tag(name = "Сертификаты", description = "Предоставляет возможность взаимодействовать с сертификатами")
@Validated
@RequiredArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;
    private static final Logger logger = LoggerFactory.getLogger(CertificateController.class);

    @Operation(summary = "Получение сертификата по идентификатору", description = "Позволит получить сертификат по идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping
    public ResponseEntity<ResponseDto<CertificateForAdminDto>> getCertificateById(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer certificateId) throws EntityNotFoundException {
        logger.info("Получение сертификата по id");
        return ResponseEntity.ok(ResponseDto.buildResponse(certificateService.getCertificateById(certificateId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение всех сертификатов", description = "Позволит получить все сертификаты")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<CertificateForAdminDto>>> getAllCertificates() throws EntitiesNotFoundException {
        logger.info("Получение всех сертификатов");
        return ResponseEntity.ok(ResponseDto.buildResponse(certificateService.getAllCertificateForAdmin(), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Создание сертификата", description = "Позволит создать сертификат")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<CertificateForAdminDto>> createCertificate(@ModelAttribute @Valid CertificateForSaveAdminDto certificateForSaveAdminDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Создание сертификата");
        /*return ResponseEntity.ok(ResponseDto.buildResponse(certificateService.saveCertificate(certificateForSaveAdminDto), ResponseState.SUCCESS,"Success"));*/
        return ResponseEntity.ok(ResponseDto.buildResponse(null, ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Редактирование сертификата", description = "Позволит отредактировать сертификат")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<CertificateForAdminDto>> updateCertificate(@ModelAttribute @Valid CertificateForUpdateAdminDto certificateForUpdateAdminDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Редактирование сертификата");
        return ResponseEntity.ok(ResponseDto.buildResponse(null, ResponseState.SUCCESS,"Success"));
        /*return ResponseEntity.ok(ResponseDto.buildResponse(certificateService.updateCertificate(certificateForUpdateAdminDto), ResponseState.SUCCESS,"Success"));*/
    }

    @Operation(summary = "Удаление сертификата", description = "Позволит удалить сертификат")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<Boolean>> deleteCertificate(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer certificateId) {
        logger.info("Удаление сертификата");
        return ResponseEntity.ok(ResponseDto.buildResponse(certificateService.deleteCertificateById(certificateId), ResponseState.SUCCESS,"Success"));
    }
}
