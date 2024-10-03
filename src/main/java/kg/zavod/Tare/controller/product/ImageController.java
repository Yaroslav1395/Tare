package kg.zavod.Tare.controller.product;

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
import kg.zavod.Tare.dto.product.image.ImageDto;
import kg.zavod.Tare.dto.product.image.ImageForSaveDto;
import kg.zavod.Tare.dto.product.image.ImageForUpdateDto;
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.service.product.ImageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("image")
@Tag(name = "Картинка продукта", description = "Предоставляет возможность взаимодействовать с картинками продуктов")
@Validated
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Operation(summary = "Получение картинки по идентификатору", description = "Позволит получить картинку по идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping
    public ResponseEntity<ResponseDto<ImageDto>> getImageById(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer imageId) throws EntityNotFoundException {
        logger.info("Получение картинки по id");
        return ResponseEntity.ok(ResponseDto.buildResponse(imageService.getImageById(imageId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение всех картинок", description = "Позволит получить все картинки")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<ImageDto>>> getAllImages() throws EntitiesNotFoundException {
        logger.info("Получение всех картинок");
        return ResponseEntity.ok(ResponseDto.buildResponse(imageService.getAllImages(), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Создание картинки", description = "Позволит создать картинку")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PostMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<ImageDto>> createImage(@RequestBody @Valid ImageForSaveDto imageForSaveDto) throws EntityNotFoundException {
        logger.info("Создание картинки");
        return ResponseEntity.ok(ResponseDto.buildResponse(imageService.saveImage(imageForSaveDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Редактирование картинки", description = "Позволит отредактировать картинку")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PutMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<ImageDto>> updateImage(@RequestBody @Valid ImageForUpdateDto imageForUpdateDto) throws EntityNotFoundException {
        logger.info("Редактирование картинки");
        return ResponseEntity.ok(ResponseDto.buildResponse(imageService.updateImage(imageForUpdateDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Удаление картинки", description = "Позволит удалить картинку")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<Boolean>> deleteImage(
            @RequestParam @NotNull(message = "Id цвета не может быть null")
            @Min(value = 1, message = "Id цвета не может быть меньше 1-го") Integer imageId) {
        logger.info("Удаление картинки");
        return ResponseEntity.ok(ResponseDto.buildResponse(imageService.deleteImageById(imageId), ResponseState.SUCCESS,"Success"));
    }
}
