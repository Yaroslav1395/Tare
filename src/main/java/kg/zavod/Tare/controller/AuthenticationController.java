package kg.zavod.Tare.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import kg.zavod.Tare.dto.ResponseDto;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.sequrity.JwtRequest;
import kg.zavod.Tare.dto.sequrity.JwtResponse;
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Авторизация", description = "Предоставляет возможность авторизации пользователя")
public class AuthenticationController {
    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Operation(
            summary = "Аутентификация пользователя",
            description = "Позволит произвести аутентификацию пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<JwtResponse>> login(@Valid @RequestBody JwtRequest jwtRequest) throws EntityNotFoundException {
        logger.info("Аутентификация пользователя");
        return ResponseEntity.ok(authService.login(jwtRequest));
    }

    @Operation(
            summary = "Обновление токена",
            description = "Позволит обновить токен")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto<JwtResponse>> refreshToken(@RequestParam String refreshToken) throws EntityNotFoundException {
        return ResponseEntity.ok(ResponseDto.buildResponse(authService.refreshToken(refreshToken), ResponseState.SUCCESS,"Success"));
    }

    @Operation(
            summary = "Редактирование пароля пользователя",
            description = "Позволит отредактировать пароль пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @PutMapping("/password")
    public ResponseEntity<ResponseDto<JwtResponse>> updatePassword(
            @RequestParam @Valid
            @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{12,}$",
                    message = "Пароль должен иметь длину не менее 12 символов и содержать хотя бы одну заглавную букву, две цифры и один специальный символ"
            ) String newPassword) throws EntityNotFoundException {
        logger.info("Обновление пароля пользователя");
        return ResponseEntity.ok(authService.updatePassword(newPassword));
    }

    @Operation(
            summary = "Выход из приложения",
            description = "Позволит пользователю выйти из приложения")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "500", description = "Произошла ошибка на сервере")})
    @GetMapping("/logout")
    public ResponseEntity<ResponseDto<Boolean>> logout() throws EntityNotFoundException {
        logger.info("Запрос на выход из приложения");
        return ResponseEntity.ok(ResponseDto.buildResponse(authService.logout(), ResponseState.SUCCESS,"Success"));
    }

}
