package kg.zavod.Tare.controller.delivery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.zavod.Tare.dto.ResponseDto;
import kg.zavod.Tare.dto.deliviry.DeliveryTable;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.service.delivery.DeliveryTableService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("delivery/table")
@Tag(name = "Таблица доставки", description = "Предоставляет возможность взаимодействовать с таблицей доставки")
@Validated
@RequiredArgsConstructor
public class DeliveryTableController {
    private final DeliveryTableService deliveryTableService;
    private static final Logger logger = LoggerFactory.getLogger(DeliveryTableController.class);

    @Operation(summary = "Получение таблицы доставки", description = "Позволит получить таблицу доставки для отображения")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping
    public ResponseEntity<ResponseDto<DeliveryTable>> getDeliveryTable() throws EntitiesNotFoundException {
        logger.info("Получение таблицы доставки");
        return ResponseEntity.ok(ResponseDto.buildResponse(deliveryTableService.getDeliveryTable(), ResponseState.SUCCESS,"Success"));
    }
}
