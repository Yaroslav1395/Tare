package kg.zavod.Tare.service.auth;

import kg.zavod.Tare.dto.ResponseDto;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.sequrity.JwtRequest;
import kg.zavod.Tare.dto.sequrity.JwtResponse;

public interface AuthService {
    /**
     * Метод производит аутентификацию и создает токен
     * @param loginRequest - данные для аутентификации
     * @return - ответ с токенами
     * @throws EntityNotFoundException - выбрасывается если пользователь не найден
     */
    ResponseDto<JwtResponse> login(JwtRequest loginRequest) throws EntityNotFoundException;


    /**
     * Метод производит обновление токенов
     * @param refreshToken - токен обновления
     * @return - новые токены
     * @throws EntityNotFoundException - выбрасывается если пользователь не найден
     */
    JwtResponse refreshToken(String refreshToken) throws EntityNotFoundException;

    /**
     * Метод позволяет обновить пароль пользователя
     * @param password - новый пароль
     * @return - новый токен
     */
    ResponseDto<JwtResponse> updatePassword(String password) throws EntityNotFoundException;

    /**
     * Метод позволяет пользователю выйти из приложения
     * @return - успешный выход
     * @throws EntityNotFoundException - в случае если пользователь не найден
     */
    Boolean logout() throws EntityNotFoundException;
}
