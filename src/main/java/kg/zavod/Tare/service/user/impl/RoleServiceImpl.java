package kg.zavod.Tare.service.user.impl;

import kg.zavod.Tare.controller.user.RoleController;
import kg.zavod.Tare.domain.user.RoleEntity;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.role.RoleDto;
import kg.zavod.Tare.dto.role.RoleForSaveDto;
import kg.zavod.Tare.mapper.role.RoleListMapper;
import kg.zavod.Tare.mapper.role.RoleMapper;
import kg.zavod.Tare.repository.user.RoleRepository;
import kg.zavod.Tare.service.user.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final RoleListMapper roleListMapper;
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    /**
     * Метод позволяет получить роль по id
     * @param id - идентификатор роли
     * @throws EntityNotFoundException - в случае если роль не найдена
     * @return - найденная роль
     */
    @Override
    public RoleDto getById(Integer id) throws EntityNotFoundException {
        logger.info("Поиск роли по id");
        RoleEntity role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("По id ничего не найдено"));
        return roleMapper.mapToRoleDto(role);
    }
    /**
     * Метод позволяет получить все роли
     * @throws EntitiesNotFoundException - в случае если ничего не найдено
     * @return - список ролей
     */
    @Override
    public List<RoleDto> getAll() throws EntitiesNotFoundException {
        logger.info("Поиск всех ролей");
        List<RoleEntity> roles = roleRepository.findAll();
        if(roles.isEmpty()) throw new EntitiesNotFoundException("Ни одной роли не найдено");
        return roleListMapper.mapToRoleDtoList(roles);
    }

    /**
     * Метод позвоялет создать новую роль
     * @param roleForSave - роль для сохранения
     * @throws DuplicateEntityException - в случае если пытаются создать дубликат
     * @return - сохраненная роль
     */
    @Override
    public RoleDto saveRole(RoleForSaveDto roleForSave) throws DuplicateEntityException {
        logger.info("Сохранение новой роли");
        checkRoleForDuplicate(roleForSave.getRole());
        RoleEntity savedRole = roleRepository.save(roleMapper.mapToRoleEntity(roleForSave));
        return roleMapper.mapToRoleDto(savedRole);
    }

    /**
     * Метод позволяет отредактировать роль
     * @param roleForUpdate - роль для редактирования
     * @throws DuplicateEntityException - в случае если пытаются создать дубликат
     * @return - отредактированная роль
     */
    @Override
    public RoleDto updateRole(RoleDto roleForUpdate) throws DuplicateEntityException {
        logger.info("Редактирование роли");
        checkRoleForDuplicate(roleForUpdate.getRole());
        RoleEntity role = roleMapper.mapToRoleEntity(roleForUpdate);
        RoleEntity updatedRole = roleRepository.save(role);
        return roleMapper.mapToRoleDto(updatedRole);
    }

    /**
     * Метод позволяет удалить роль по id
     * @param id - идентификатор роли
     * @return - роль удалена
     */
    @Override
    public Boolean deleteById(Integer id) {
        logger.info("Удаление роли");
        roleRepository.deleteById(id);
        return !roleRepository.existsById(id);
    }

    /**
     * Метод проверяет существует ли роль в базе
     * @param role - название роли
     * @throws DuplicateEntityException - в случае если существует
     */
    private void checkRoleForDuplicate(String role) throws DuplicateEntityException {
        logger.info("Проверка роли на дубликат");
        Optional<RoleEntity> existingRole = roleRepository.findByRole(role);
        if (existingRole.isPresent()) {
            throw new IllegalArgumentException("Данная роль уже существует в базе: " + role);
        }
    }
}
