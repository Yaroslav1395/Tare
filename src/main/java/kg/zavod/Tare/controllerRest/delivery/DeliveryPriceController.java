package kg.zavod.Tare.controllerRest.delivery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import kg.zavod.Tare.dto.ResponseDto;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceDto;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceForSaveDto;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceForUpdateDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.service.delivery.DistrictCapacityPriceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@RequestMapping("delivery/price")
@Tag(name = "Цена доставки", description = "Предоставляет возможность взаимодействовать с ценой доставки")
@Validated
@RequiredArgsConstructor
public class DeliveryPriceController {
    private final DistrictCapacityPriceService districtCapacityPriceService;
    private static final Logger logger = LoggerFactory.getLogger(DeliveryPriceController.class);

    @Operation(summary = "Получение цены доставки по идентификатору", description = "Позволит получить цену доставки по идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping
    public ResponseEntity<ResponseDto<DeliveryPriceDto>> getDeliveryPriceById(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer deliveryPriceId) throws EntityNotFoundException {
        logger.info("Получение цены доставки по id");
        return ResponseEntity.ok(ResponseDto.buildResponse(districtCapacityPriceService.getDeliveryPriceById(deliveryPriceId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение всех цен доставки", description = "Позволит получить все цены доставки")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<DeliveryPriceDto>>> getAllDeliveryPrices() throws EntitiesNotFoundException {
        logger.info("Получение всех цен доставки");
        return ResponseEntity.ok(ResponseDto.buildResponse(districtCapacityPriceService.getAllDeliveryPrices(), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение всех цен доставки для района", description = "Позволит получить все цены доставки в определенный район")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/all/for/district")
    public ResponseEntity<ResponseDto<List<DeliveryPriceDto>>> getAllDeliveryPricesForDistrict(
            @RequestParam @NotNull(message = "Id района не может быть null")
            @Min(value = 1, message = "Id района не может быть меньше 1") Integer districtId
    ) throws EntitiesNotFoundException {
        logger.info("Получение всех цен доставки");
        return ResponseEntity.ok(ResponseDto.buildResponse(districtCapacityPriceService.getAllDeliveryPricesByDistrictId(districtId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Получение всех цен доставки для объема", description = "Позволит получить все цены доставки для определенного объема")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/all/for/capacity")
    public ResponseEntity<ResponseDto<List<DeliveryPriceDto>>> getAllDeliveryPricesForCapacity(
            @RequestParam @NotNull(message = "Id объема не может быть null")
            @Min(value = 1, message = "Id объема не может быть меньше 1") Integer capacityId
    ) throws EntitiesNotFoundException {
        logger.info("Получение всех цен доставки");
        return ResponseEntity.ok(ResponseDto.buildResponse(districtCapacityPriceService.getAllDeliveryPricesByCapacityId(capacityId), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Создание цены доставки", description = "Позволит создать цену доставки")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PostMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<DeliveryPriceDto>> createDeliveryPrice(@ModelAttribute @Valid DeliveryPriceForSaveDto deliveryPriceForSaveDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Создание цены доставки");
        return ResponseEntity.ok(ResponseDto.buildResponse(districtCapacityPriceService.saveDeliveryPrice(deliveryPriceForSaveDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Редактирование цены доставки", description = "Позволит отредактировать цену доставки")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PutMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<DeliveryPriceDto>> updateDeliveryPrice(@ModelAttribute @Valid DeliveryPriceForUpdateDto deliveryPriceForUpdateDto) throws EntityNotFoundException {
        logger.info("Редактирование цены доставки");
        return ResponseEntity.ok(ResponseDto.buildResponse(districtCapacityPriceService.updateDeliveryPrice(deliveryPriceForUpdateDto), ResponseState.SUCCESS,"Success"));
    }

    @Operation(summary = "Удаление цены доставки", description = "Позволит удалить цену доставки")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('DEVELOPER', 'ADMIN')")
    public ResponseEntity<ResponseDto<Boolean>> deleteDeliveryPrice(
            @RequestParam @NotNull(message = "Id не может быть null")
            @Min(value = 1, message = "Id не может быть меньше 1") Integer deliveryPriceId) {
        logger.info("Удаление цены доставки");
        return ResponseEntity.ok(ResponseDto.buildResponse(districtCapacityPriceService.deleteDeliveryPriceById(deliveryPriceId), ResponseState.SUCCESS,"Success"));
    }
}
