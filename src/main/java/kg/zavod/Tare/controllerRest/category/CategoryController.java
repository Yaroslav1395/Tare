package kg.zavod.Tare.controllerRest.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import kg.zavod.Tare.dto.ResponseDto;
import kg.zavod.Tare.dto.category.CategoryDto;
import kg.zavod.Tare.dto.category.CategoryForSaveDto;
import kg.zavod.Tare.dto.category.CategoryForUpdateDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.service.category.CategoryService;
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
@RequestMapping("category")
@Tag(name = "Категории", description = "Предоставляет возможность взаимодействовать с категориями")
@Validated
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Operation(summary = "Получение категории по идентификатору", description = "Позволит получить категорию по идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping
    public ResponseEntity<ResponseDto<CategoryDto>> getCategoryById(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer categoryId) throws EntityNotFoundException {
        logger.info("Получение категории по id");
        return ResponseEntity.ok(ResponseDto.buildResponse(categoryService.getCategoryById(categoryId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение всех категорий", description = "Позволит получить все категории")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<CategoryDto>>> getAllCategories() throws EntitiesNotFoundException {
        logger.info("Получение всех категорий");
        return ResponseEntity.ok(ResponseDto.buildResponse(categoryService.getAllCategories(), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Создание категории", description = "Позволит создать категорию")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<CategoryDto>> createCategory(@ModelAttribute @Valid CategoryForSaveDto categoryForSaveDto) throws DuplicateEntityException, EntityNotFoundException {
        logger.info("Создание категории");
        return ResponseEntity.ok(ResponseDto.buildResponse(categoryService.saveCategory(categoryForSaveDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Редактирование категории", description = "Позволит отредактировать категорию")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<CategoryDto>> updateCategory(@ModelAttribute @Valid CategoryForUpdateDto categoryForUpdateDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Редактирование категории");
        return ResponseEntity.ok(ResponseDto.buildResponse(categoryService.updateCategory(categoryForUpdateDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Удаление категории", description = "Позволит удалить категорию")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<Boolean>> deleteCategory(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer categoryId) {
        logger.info("Удаление категории");
        return ResponseEntity.ok(ResponseDto.buildResponse(categoryService.deleteCategoryById(categoryId), ResponseState.SUCCESS,"Success"));
    }

}
