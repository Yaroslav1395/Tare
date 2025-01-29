package kg.zavod.Tare.controllerRest.category;

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
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.dto.subcategory.SubcategoryDto;
import kg.zavod.Tare.dto.subcategory.SubcategoryForSaveDto;
import kg.zavod.Tare.dto.subcategory.SubcategoryForUpdateDto;
import kg.zavod.Tare.service.category.SubcategoryService;
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
@RequestMapping("/rest/subcategory")
@Tag(name = "Подкатегории", description = "Предоставляет возможность взаимодействовать с подкатегориями")
@Validated
@RequiredArgsConstructor
public class SubcategoryController {
    private final SubcategoryService subcategoryService;
    private static final Logger logger = LoggerFactory.getLogger(SubcategoryController.class);

    @Operation(summary = "Получение подкатегории по идентификатору", description = "Позволит получить подкатегорию по идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping
    public ResponseEntity<ResponseDto<SubcategoryDto>> getSubcategoryById(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer subcategoryId) throws EntityNotFoundException {
        logger.info("Получение подкатегории по id");
        return ResponseEntity.ok(ResponseDto.buildResponse(subcategoryService.getSubcategoryById(subcategoryId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение всех подкатегорий", description = "Позволит получить все подкатегории")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<SubcategoryDto>>> getAllSubcategory() throws EntitiesNotFoundException {
        logger.info("Получение всех подкатегорий");
        return ResponseEntity.ok(ResponseDto.buildResponse(subcategoryService.getAllSubcategories(), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Создание подкатегории", description = "Позволит создать подкатегорию")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<SubcategoryDto>> createSubcategory(@ModelAttribute @Valid SubcategoryForSaveDto subcategoryForSaveDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Создание подкатегории");
        return ResponseEntity.ok(ResponseDto.buildResponse(subcategoryService.saveSubcategory(subcategoryForSaveDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Редактирование подкатегории", description = "Позволит отредактировать подкатегорию")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<SubcategoryDto>> updateSubcategory(@ModelAttribute @Valid SubcategoryForUpdateDto subcategoryForUpdateDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Редактирование подкатегории");
        return ResponseEntity.ok(ResponseDto.buildResponse(subcategoryService.updateSubcategory(subcategoryForUpdateDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Удаление подкатегории", description = "Позволит подудалить категорию")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<Boolean>> deleteSubcategory(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer subcategoryId) {
        logger.info("Удаление подкатегории");
        return ResponseEntity.ok(ResponseDto.buildResponse(subcategoryService.deleteSubcategoryById(subcategoryId), ResponseState.SUCCESS,"Success"));
    }
}
