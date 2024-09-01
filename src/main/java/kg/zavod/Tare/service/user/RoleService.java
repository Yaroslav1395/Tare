package kg.zavod.Tare.service.user;

import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.role.RoleDto;
import kg.zavod.Tare.dto.role.RoleForSaveDto;

import java.util.List;

public interface RoleService {
    /**
     * Метод позволяет получить роль по id
     * @param id - идентификатор роли
     * @throws EntityNotFoundException - в случае если роль не найдена
     * @return - найденная роль
     */
    RoleDto getById(Integer id) throws EntityNotFoundException;

    /**
     * Метод позволяет получить все роли
     * @throws EntitiesNotFoundException - в случае если записи не найдены
     * @return - список ролей
     */
    List<RoleDto> getAll() throws EntitiesNotFoundException;

    /**
     * Метод позвоялет создать новую роль
     * @param roleForSave - роль для сохранения
     * @throws DuplicateEntityException - в случае если пытаются создать дубликат
     * @return - сохраненная роль
     */
    RoleDto saveRole(RoleForSaveDto roleForSave) throws DuplicateEntityException;

    /**
     * Метод позволяет отредактировать роль
     * @param roleForUpdate - роль для редактирования
     * @throws DuplicateEntityException - в случае если пытаются создать дубликат
     * @return - отредактированная роль
     */
    RoleDto updateRole(RoleDto roleForUpdate) throws DuplicateEntityException;

    /**
     * Метод позволяет удалить роль по id
     * @param id - идентификатор роли
     * @return - роль удалена
     */
    Boolean deleteById(Integer id);

}
