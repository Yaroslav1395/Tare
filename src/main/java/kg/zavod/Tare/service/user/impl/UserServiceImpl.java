package kg.zavod.Tare.service.user.impl;

import jakarta.transaction.Transactional;
import kg.zavod.Tare.config.SecurityConfig;
import kg.zavod.Tare.domain.user.UserEntity;
import kg.zavod.Tare.domain.user.RoleEntity;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.user.UserForSaveDto;
import kg.zavod.Tare.dto.user.UserForUpdateDto;
import kg.zavod.Tare.dto.user.UserDto;
import kg.zavod.Tare.mapper.user.UserListMapper;
import kg.zavod.Tare.mapper.user.UserMapper;
import kg.zavod.Tare.repository.user.RoleRepository;
import kg.zavod.Tare.repository.user.UserRepository;
import kg.zavod.Tare.sequrity.UserDetailsImpl;
import kg.zavod.Tare.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;
    private final UserListMapper userListMapper;
    @Value("${password.default}")
    private String defaultPassword;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    /**
     * Метод найдет пользователя по имени
     * @param username - имя пользователя
     * @throws EntityNotFoundException - выбрасывается если пользователь не найден
     * @return - пользователь
     */
    @Override
    @Transactional
    public UserEntity getByUsername(String username) throws EntityNotFoundException {
        logger.info("Получение пользователя по его логину");
        UserEntity user =  userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с таким именем не найден"));
        return user;
    }

    /**
     * Метод обновляет пароль пользователя
     * @param newPassword - новый пароль
     * @return - статус обновления
     */
    @Override
    public UserEntity updatePassword(String newPassword) throws EntityNotFoundException {
        logger.info("Изменение пароля пользователя");
        UserDetailsImpl user = SecurityConfig.getUserFromSecurityContext();
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        UserEntity userEntity = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        userEntity.setPassword(encodedPassword);
        userEntity.setTokenUUID(UUID.randomUUID().toString());
        logger.info("Сохранение пароля пользователя");
        return userRepository.save(userEntity);
    }

    /**
     * Получучение пользователя о его id
     * @param id - идентификатор пользователя
     * @throws EntityNotFoundException - в случае если пользователь не найден
     * @return - найденный пользователь
     */
    @Override
    @Transactional
    public UserDto getUserById(Integer id) throws EntityNotFoundException {
        logger.info("Получение пользователя по id");
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с таким id нет"));
        return userMapper.mapToUserDto(user);
    }

    /**
     * Полуучение всех пользователей
     * @throws EntitiesNotFoundException - в случае если в базе нет пользователей
     * @return - списко пользователй
     */
    @Override
    @Transactional
    public List<UserDto> getAllUsers() throws EntitiesNotFoundException {
        logger.info("Получение всех пользователей");
        List<UserEntity> users = userRepository.findAll();
        if(users.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одного пользователя");
        return userListMapper.mapToUserDtoList(users);
    }

    /**
     * Создание нового пользователя
     * @param userForSave - новый пользователь которого нужно сохранить
     * @return - данные сохраненного пользователя
     */
    @Override
    @Transactional
    public UserDto createUser(UserForSaveDto userForSave) {
        logger.info("Сохранение нового пользователя");
        UserEntity user = userMapper.mapToUserEntity(userForSave);
        setRolesToUser(user, userForSave.getRolesId());
        user.setPassword(bCryptPasswordEncoder.encode(defaultPassword));
        UserEntity savedUser = userRepository.save(user);
        return userMapper.mapToUserDto(savedUser);
    }

    /**
     * Редактирование данных существующего пользователя
     * @param userForUpdate - данные которые нужно сохранить
     * @throws EntityNotFoundException - в случае если не найден пользователь для редактирования
     * @return - отредактированные данные пользователя
     */
    @Override
    @Transactional
    public UserDto updateUser(UserForUpdateDto userForUpdate) throws EntityNotFoundException {
        logger.info("Редактирование существующего пользователя");
        UserEntity user = userRepository.findById(userForUpdate.getId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с таким id не найдено"));
        userMapper.updateUserFromDto(userForUpdate, user);
        setRolesToUser(user, userForUpdate.getRolesId());
        UserEntity updatedUser = userRepository.save(user);
        return userMapper.mapToUserDto(updatedUser);
    }

    /**
     * Удаление пользователя по его идентификатору
     * @param id - идентификатор пользователя
     * @return - удален пользователь
     */
    @Override
    public Boolean deleteById(Integer id) {
        logger.info("Удаление пользователя");
        userRepository.deleteById(id);
        return !userRepository.existsById(id);
    }

    /**
     * Метод ищет роли по id и устанавливает из пользователю
     * @param user - пользователь которуому нужно установить роли
     * @param rolesFromUser - id ролей для поиска
     */
    private void setRolesToUser(UserEntity user, List<Integer> rolesFromUser){
        logger.info("Поиск ролей по id");
        List<RoleEntity> roles = roleRepository.findAllById(rolesFromUser);
        user.setRoles(new ArrayList<>(roles));
    }
}
