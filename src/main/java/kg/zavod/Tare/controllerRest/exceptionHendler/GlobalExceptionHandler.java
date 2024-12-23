package kg.zavod.Tare.controllerRest.exceptionHendler;

import jakarta.validation.ConstraintViolationException;
import kg.zavod.Tare.dto.ResponseDto;
import kg.zavod.Tare.dto.exception.*;
import kg.zavod.Tare.dto.state.ResponseState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Метод отрабатывает если в базе не найдена запись
     * @param entityNotFoundException - исключение в случае если в базе не найдена запись
     * @return - ответ с ошибкой
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDto<String>> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        logger.error("Запись в базе не найдена. Ошибка: {}", entityNotFoundException.getMessage());
        return ResponseEntity.badRequest().body(ResponseDto.buildResponse(ResponseState.FAIL, entityNotFoundException.getMessage()));
    }

    /**
     * Метод отрабатывает если в базе не найдены записи
     * @param entitiesNotFoundException - исключение в случае если в базе не найдены записи
     * @return - ответ с ошибкой и пустым списком
     */
    @ExceptionHandler(EntitiesNotFoundException.class)
    public ResponseEntity<ResponseDto<List<String>>> handleEntitiesNotFoundException(EntitiesNotFoundException entitiesNotFoundException) {
        logger.error("Записи в базе не найдены. Ошибка: {}", entitiesNotFoundException.getMessage());
        return ResponseEntity.ok().body(ResponseDto.buildResponse(Collections.emptyList(), ResponseState.INFO, entitiesNotFoundException.getMessage()));
    }

    /**
     * Метод отрабатывает, если создаваемая сущность уже существует
     * @param duplicateEntityException - исключение в случае если создаваемая сущность уже существует
     * @return - ответ с ошибкой
     */
    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ResponseDto<String>> handleDuplicateEntityException(DuplicateEntityException duplicateEntityException) {
        logger.error("Новая запись дублирует существующую. Ошибка: {}", duplicateEntityException.getMessage());
        return ResponseEntity.badRequest().body(ResponseDto.buildResponse(ResponseState.DUPLICATE, duplicateEntityException.getMessage()));
    }

    /**
     * Метод отрабатывает, если при удалении сущности имеются ссылочные связи в базе
     * @param deleteException - исключение в случае если удаляемая сущность имеет связь с другой сущностью
     * @return - ответ с ошибкой
     */
    @ExceptionHandler(DeleteException.class)
    public ResponseEntity<ResponseDto<String>> handleDeleteException(DeleteException deleteException) {
        logger.error("Удаляемая сущность имеет связи с другой сущностью. Ошибка: {}", deleteException.getMessage());
        return ResponseEntity.badRequest().body(ResponseDto.buildResponse(ResponseState.FAIL, deleteException.getMessage()));
    }

    /**
     * Метод отрабатывает, если при преобразовании MultipartFile в массив байт возникла ошибка
     * @param multipartFileParseException - исключение в случае если при преобразовании MultipartFile в массив байт возникла ошибка
     * @return - ответ с ошибкой
     */
    @ExceptionHandler(MultipartFileParseException.class)
    public ResponseEntity<ResponseDto<String>> handleMultipartParseException(MultipartFileParseException multipartFileParseException) {
        logger.error("Удаляемая сущность имеет связи с другой сущностью. Ошибка: {}", multipartFileParseException.getMessage());
        return ResponseEntity.badRequest().body(ResponseDto.buildResponse(ResponseState.FAIL, multipartFileParseException.getMessage()));
    }

    /**
     * Метод отрабатывает, если нарушена уникальность поля
     * @param dataIntegrityViolationException - исключение в случае если нарушена уникальность поля
     * @return - ответ с ошибкой
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDto<String>> handleDataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException) {
        logger.error("Удаляемая сущность имеет связи с другой сущностью. Ошибка: {}", dataIntegrityViolationException.getMessage());
        return ResponseEntity.badRequest().body(ResponseDto.buildResponse(ResponseState.ERROR, "Нарушена уникальность поля"));
    }

    /**
     * Метод отрабатывает, если имеется попытка манипуляции над статичными данными
     * @param staticDataException - исключение в случае если имеется попытка манипуляции над статичными данными
     * @return - ответ с ошибкой
     */
    @ExceptionHandler(StaticDataException.class)
    public ResponseEntity<ResponseDto<String>> handleStaticDataExceptionException(StaticDataException staticDataException) {
        logger.error("Манипуляция над статичными данными. Ошибка: {}", staticDataException.getMessage());
        return ResponseEntity.ok().body(ResponseDto.buildResponse(ResponseState.INFO, staticDataException.getMessage()));
    }


    /**
     * Метод отрабатывает если возникают ошибки при валидации
     * @param ex - ошибка, которая возникает во время обработки валидации
     * @return - ответ с ошибкой
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<List<String>>> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        logger.error("Валидация объекта не прошла. Ошибка: {}", errors);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResponseDto.buildResponse(errors, ResponseState.ERROR, "Validation error"));
    }
    /**
     * Метод обрабатывает ошибки валидации параметров
     * @param ex - ошибка, которая возникает во время обработки валидации
     * @return - ответ с ошибкой
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDto<String>> handleValidationException(ConstraintViolationException ex) {
        logger.error("Валидация параметров не прошла. Ошибка: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResponseDto.buildResponse(ResponseState.ERROR, ex.getMessage()));
    }
}
