package kg.zavod.Tare.service.auth.impl;

import jakarta.transaction.Transactional;
import kg.zavod.Tare.config.SecurityConfig;
import kg.zavod.Tare.domain.user.UserEntity;
import kg.zavod.Tare.dto.ResponseDto;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.sequrity.JwtRequest;
import kg.zavod.Tare.dto.sequrity.JwtResponse;
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.dto.role.RoleDto;
import kg.zavod.Tare.mapper.role.RoleListMapper;
import kg.zavod.Tare.repository.user.UserRepository;
import kg.zavod.Tare.sequrity.JwtTokenProvider;
import kg.zavod.Tare.sequrity.UserDetailsImpl;
import kg.zavod.Tare.service.auth.AuthService;
import kg.zavod.Tare.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RoleListMapper roleListMapper;
    /**
     * Метод производит аутентификацию и создает токен
     * @param loginRequest - данные для аутентификации
     * @return - ответ с токенами
     * @throws EntityNotFoundException - выбрасывается если пользователь не найден
     */
    @Override
    @Transactional
    public ResponseDto<JwtResponse> login(JwtRequest loginRequest) throws EntityNotFoundException {
        return giveToken(loginRequest);
    }

    /**
     * Метод производит обновление токенов
     * @param refreshToken - токен обновления
     * @return - новые токены
     * @throws EntityNotFoundException - выбрасывается если пользователь не найден
     */
    @Override
    @Transactional
    public JwtResponse refreshToken(String refreshToken) throws EntityNotFoundException {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }

    /**
     * Метод позволяет обновить пароль пользователя
     * @param password - новый пароль
     * @return - новый токен
     */
    @Override
    @Transactional
    public ResponseDto<JwtResponse> updatePassword(String password) throws EntityNotFoundException {
        UserEntity userEntity = userService.updatePassword(password);
        List<RoleDto> roles = roleListMapper.mapToRoleDtoList(userEntity.getRoles());
        return ResponseDto.buildResponse(
                JwtResponse.builder()
                        .id(userEntity.getId())
                        .username(userEntity.getUsername())
                        .roles(roles)
                        .accessToken(jwtTokenProvider.createAccessToken(userEntity.getId(), userEntity.getUsername(), userEntity.getRoles(), userEntity.getTokenUUID()))
                        .refreshToken(jwtTokenProvider.createRefreshToken(userEntity.getId(), userEntity.getUsername(), userEntity.getTokenUUID()))
                        .build(),
                ResponseState.SUCCESS, "Пароль обновлен успешно");
    }

    /**
     * Метод позволяет пользователю выйти из приложения
     * @return - успешный выход
     * @throws EntityNotFoundException - в случае если пользователь не найден
     */
    @Override
    public Boolean logout() throws EntityNotFoundException {
        UserDetailsImpl userDetails = SecurityConfig.getUserFromSecurityContext();
        String uuidToken = UUID.randomUUID().toString();
        UserEntity userEntity = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        userEntity.setTokenUUID(uuidToken);
        UserEntity updatedUser = userRepository.save(userEntity);
        return updatedUser.getTokenUUID().equals(uuidToken);
    }

    /**
     * Метод формирует токен и выдвет его
     * @param loginRequest - запрос на аутентификацию
     * @return - ответ с токеном
     * @throws EntityNotFoundException - в случае если пользователь не найден
     */
    private ResponseDto<JwtResponse> giveToken(JwtRequest loginRequest) throws EntityNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserEntity userEntity = userService.getByUsername(loginRequest.getUsername());
        List<RoleDto> roles = roleListMapper.mapToRoleDtoList(userEntity.getRoles());
        String uuidToken = UUID.randomUUID().toString();
        userEntity.setTokenUUID(uuidToken);
        userRepository.save(userEntity);
        return ResponseDto.buildResponse(
                JwtResponse.builder()
                        .id(userEntity.getId())
                        .username(userEntity.getUsername())
                        .roles(roles)
                        .accessToken(jwtTokenProvider.createAccessToken(userEntity.getId(), loginRequest.getUsername(), userEntity.getRoles(), uuidToken))
                        .refreshToken(jwtTokenProvider.createRefreshToken(userEntity.getId(), loginRequest.getUsername(), uuidToken))
                        .build(),
                ResponseState.SUCCESS, "Successes");
    }
}
