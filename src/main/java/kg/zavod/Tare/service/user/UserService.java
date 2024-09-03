package kg.zavod.Tare.service.user;

import kg.zavod.Tare.domain.user.UserEntity;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.user.UserForSaveDto;
import kg.zavod.Tare.dto.user.UserForUpdateDto;
import kg.zavod.Tare.dto.user.UserDto;

import java.util.List;

public interface UserService {
    /**
     * Метод найдет пользователя по имени
     * @param username - имя пользователя
     * @throws EntityNotFoundException - выбрасывается если пользователь не найден
     * @return - пользователь
     */
    UserEntity getByUsername(String username) throws EntityNotFoundException;

    /**
     * Метод обновляет пароль пользователя
     * @param newPassword - новый пароль
     * @throws EntityNotFoundException - в случае если не найден пользователь для редактирования
     * @return - статус обновления
     */
    UserEntity updatePassword(String newPassword) throws EntityNotFoundException;

    /**
     * Получение пользователя о его id
     * @param id - идентификатор пользователя
     * @throws EntityNotFoundException - в случае если пользователь не найден
     * @return - найденный пользователь
     */
    UserDto getUserById(Integer id) throws EntityNotFoundException;

    /**
     * Получение всех пользователей
     * @throws EntitiesNotFoundException - в случае если в базе нет пользователей
     * @return - список пользователй
     */
    List<UserDto> getAllUsers() throws EntitiesNotFoundException;

    /**
     * Создание нового пользователя
     * @param userForSave - новый пользователь которого нужно сохранить
     * @throws DuplicateEntityException - в случае если пользователь с таким логином существует
     * @return - данные сохраненного пользователя
     */
    UserDto createUser(UserForSaveDto userForSave) throws DuplicateEntityException;

    /**
     * Редактирование данных существующего пользователя
     * @param userForUpdate - данные которые нужно сохранить
     * @throws EntityNotFoundException - в случае если пользователь не найден для редактирования
     * @throws DuplicateEntityException - в случае если пользователь с таким логином существует
     * @return - отредактированные данные пользователя
     */
    UserDto updateUser(UserForUpdateDto userForUpdate) throws EntityNotFoundException, DuplicateEntityException;

    /**
     * Удаление пользователя по его идентификатору
     * @param id - идентификатор пользователя
     * @return - удален пользователь
     */
    Boolean deleteById(Integer id);

}
