package kg.zavod.Tare.service.user;

import kg.zavod.Tare.domain.user.UserEntity;
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
     * Получучение пользователя о его id
     * @param id - идентификатор пользователя
     * @throws EntityNotFoundException - в случае если пользователь не найден
     * @return - найденный пользователь
     */
    UserDto getUserById(Integer id) throws EntityNotFoundException;

    /**
     * Полуучение всех пользователей
     * @throws EntitiesNotFoundException - в случае если в базе нет пользователей
     * @return - списко пользователй
     */
    List<UserDto> getAllUsers() throws EntitiesNotFoundException;

    /**
     * Создание нового пользователя
     * @param userForSave - новый пользователь которого нужно сохранить
     * @return - данные сохраненного пользователя
     */
    UserDto createUser(UserForSaveDto userForSave);

    /**
     * Редактирование данных существующего пользователя
     * @param userForUpdate - данные которые нужно сохранить
     * @throws EntityNotFoundException - в случае если пользователь не найден для редактирования
     * @return - отредактированные данные пользователя
     */
    UserDto updateUser(UserForUpdateDto userForUpdate) throws EntityNotFoundException;

    /**
     * Удаление пользователя по его идентификатору
     * @param id - идентификатор пользователя
     * @return - удален пользователь
     */
    Boolean deleteById(Integer id);

}
