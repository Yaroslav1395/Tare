package kg.zavod.Tare.controller.notice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import kg.zavod.Tare.controller.vacancy.VacancyController;
import kg.zavod.Tare.dto.ResponseDto;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.notice.NoticeDto;
import kg.zavod.Tare.dto.notice.NoticeForSaveDto;
import kg.zavod.Tare.dto.notice.NoticeForUpdateDto;
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notice")
@Tag(name = "Новости", description = "Предоставляет возможность взаимодействовать с новостями")
@Validated
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;
    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @Operation(summary = "Получение новости по идентификатору", description = "Позволит получить новость по идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    //@PreAuthorize("hasAnyAuthority('DEVELOPER', 'SUPER_ADMIN', 'ADMIN_ORPK', 'ORPK_EMPLOYEE')")
    @GetMapping
    public ResponseEntity<ResponseDto<NoticeDto>> getNoticeById(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer noticeId) throws EntityNotFoundException {
        logger.info("Получение новости по id");
        return ResponseEntity.ok(ResponseDto.buildResponse(noticeService.getNoticeById(noticeId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение всех новостей", description = "Позволит получить все новости")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/all")
    //@PreAuthorize("hasAnyAuthority('DEVELOPER', 'SUPER_ADMIN', 'ADMIN_ORPK', 'ORPK_EMPLOYEE')")
    public ResponseEntity<ResponseDto<List<NoticeDto>>> getAllNotices() throws EntitiesNotFoundException {
        logger.info("Получение всех новостей");
        return ResponseEntity.ok(ResponseDto.buildResponse(noticeService.getAllNotices(), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение всех активных новостей", description = "Позволит получить все активные новости")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/all/active")
    //@PreAuthorize("hasAnyAuthority('DEVELOPER', 'SUPER_ADMIN', 'ADMIN_ORPK', 'ORPK_EMPLOYEE')")
    public ResponseEntity<ResponseDto<List<NoticeDto>>> getAllActiveNotices() throws EntitiesNotFoundException {
        logger.info("Получение всех активных новостей");
        return ResponseEntity.ok(ResponseDto.buildResponse(noticeService.getAllActiveNotices(), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Создание новости", description = "Позволит создать новость")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyAuthority('DEVELOPER', 'SUPER_ADMIN')")
    public ResponseEntity<ResponseDto<NoticeDto>> createNotice(@ModelAttribute @Valid NoticeForSaveDto noticeForSaveDto) throws EntityNotFoundException {
        logger.info("Создание новости");
        return ResponseEntity.ok(ResponseDto.buildResponse(noticeService.saveNotice(noticeForSaveDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Редактирование новости", description = "Позволит отредактировать новость")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyAuthority('DEVELOPER', 'SUPER_ADMIN')")
    public ResponseEntity<ResponseDto<NoticeDto>> updateNotice(@ModelAttribute @Valid NoticeForUpdateDto noticeForUpdateDto) throws EntityNotFoundException {
        logger.info("Редактирование новости");
        return ResponseEntity.ok(ResponseDto.buildResponse(noticeService.updateNotice(noticeForUpdateDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Удаление новости", description = "Позволит удалить новость")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @DeleteMapping
    //@PreAuthorize("hasAnyAuthority('DEVELOPER', 'SUPER_ADMIN')")
    public ResponseEntity<ResponseDto<Boolean>> deleteNotice(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer noticeId) {
        logger.info("Удаление новости");
        return ResponseEntity.ok(ResponseDto.buildResponse(noticeService.deleteNoticeById(noticeId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Изменение активности новости", description = "Позволит изменить активность новости")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PutMapping("/activity")
    public ResponseEntity<ResponseDto<Boolean>> changeNoticeActivity(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer noticeId,
            @RequestParam @NotNull(message = "Id не может быть null") Boolean isActive) {
        logger.info("Изменение активности новости");
        return ResponseEntity.ok(ResponseDto.buildResponse(noticeService.changeNoticeActivityById(noticeId, isActive), ResponseState.SUCCESS,"Success"));
    }
}
