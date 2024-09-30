package kg.zavod.Tare.controller.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import kg.zavod.Tare.dto.ResponseDto;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.product.*;
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
@Tag(name = "Продукт", description = "Предоставляет возможность взаимодействовать с продуктом")
@Validated
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Operation(summary = "Получение продукта по id", description = "Позволит получить продукт по его id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping
    //@PreAuthorize("hasAnyAuthority('DEVELOPER', 'SUPER_ADMIN')")
    public ResponseEntity<ResponseDto<ProductDto>> getProductById(
            @RequestParam @NotNull(message = "Id продукта не может быть null")
            @Min(value = 1, message = "Id продукта не может быть меньше 1-го") Integer productId) throws EntityNotFoundException {
        logger.info("Получение продукта по id");
        return ResponseEntity.ok(ResponseDto.buildResponse(productService.getProductById(productId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение продуктов по id подкатегории", description = "Позволит получить продуктов по id подкатегории")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/subcategory")
    //@PreAuthorize("hasAnyAuthority('DEVELOPER', 'SUPER_ADMIN')")
    public ResponseEntity<ResponseDto<PageForProduct>> getProductBySubcategoryId(
            @RequestParam @NotNull(message = "Id подкатегории не может быть null")
            @Min(value = 1, message = "Id подкатегории не может быть меньше 1-го") Integer subcategoryId,
            @Min(value = 1, message = "Страница не может быть меньше 1-го") Integer page,
            @Min(value = 1, message = "Размер страницы не может быть меньше 1-го") Integer size) throws EntitiesNotFoundException {
        logger.info("Получение продуктов по id подкатегории");
        Pageable pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.ok(ResponseDto.buildResponse(productService.getProductsBySubcategoryId(subcategoryId, pageable), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Создание продукта", description = "Позволит создать продукт")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyAuthority('DEVELOPER', 'SUPER_ADMIN')")
    public ResponseEntity<ResponseDto<ProductDto>> createProduct(@RequestBody ProductForSaveDto product) throws EntityNotFoundException, EntitiesNotFoundException {
        logger.info("Создание продукта");
        /*ImageForSaveWithProductDto imageForSaveDto = ImageForSaveWithProductDto.builder()
                .productImage(image)
                .colorId(1)
                .build();
        ImageForSaveWithProductDto imageForSaveDto1 = ImageForSaveWithProductDto.builder()
                .productImage(image)
                .colorId(2)
                .build();
        CharacteristicValueForSaveWithProductDto character1 = CharacteristicValueForSaveWithProductDto.builder()
                .characteristicId(1)
                .value(100)
                .build();
        CharacteristicValueForSaveWithProductDto character2 = CharacteristicValueForSaveWithProductDto.builder()
                .characteristicId(2)
                .value(200)
                .build();
        List<CharacteristicValueForSaveWithProductDto> characteristics = List.of(character1, character2);
        List<ImageForSaveWithProductDto> images = List.of(imageForSaveDto, imageForSaveDto1);

        ProductForSaveDto product = ProductForSaveDto.builder()
                .name("Продукт")
                .idFromFactoryBd(1)
                .subcategoryId(1)
                .characteristicsValues(characteristics)
                .images(images)
                .build();*/
        return ResponseEntity.ok(ResponseDto.buildResponse(productService.saveProduct(product), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Редактирование продукта", description = "Позволит отредактировать продукт")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyAuthority('DEVELOPER', 'SUPER_ADMIN')")
    public ResponseEntity<ResponseDto<ProductDto>> updateProduct(@RequestBody ProductForUpdateDto product) throws EntityNotFoundException {
        logger.info("Редактирование продукта");
        return ResponseEntity.ok(ResponseDto.buildResponse(productService.updateProduct(product), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Удаление продукта", description = "Позволит удалить продукт")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @DeleteMapping
    //@PreAuthorize("hasAnyAuthority('DEVELOPER', 'SUPER_ADMIN')")
    public ResponseEntity<ResponseDto<Boolean>> deleteProduct(
            @RequestParam @NotNull(message = "Id продукта не может быть null")
            @Min(value = 1, message = "Id продукта не может быть меньше 1-го") Integer productId){
        logger.info("Удаление продукта по id");
        return ResponseEntity.ok(ResponseDto.buildResponse(productService.deleteProduct(productId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение ссылки на whats app", description = "Позволяет получить ссылку на whats app c заполненным сообщением")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PostMapping("/basket")
    //@PreAuthorize("hasAnyAuthority('DEVELOPER', 'SUPER_ADMIN')")
    public ResponseEntity<ResponseDto<String>> getUrlToWhatsAppWithProductBasket(@RequestBody List<@Valid ProductFromBasketDro> products){
        logger.info("Формирование ссылки на whats app");
        return ResponseEntity.ok(ResponseDto.buildResponse(productService.getUrlForWhatsAppWithProductBasket(products), ResponseState.SUCCESS,"Success"));
    }
}
