package kg.zavod.Tare.sequrity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import kg.zavod.Tare.domain.user.RoleEntity;
import kg.zavod.Tare.domain.user.UserEntity;
import kg.zavod.Tare.dto.exception.AccessDeniedException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.sequrity.JwtResponse;
import kg.zavod.Tare.dto.role.RoleDto;
import kg.zavod.Tare.mapper.role.RoleListMapper;
import kg.zavod.Tare.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;
    private final RoleListMapper roleListMapper;
    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Метод создает токен доступа к ресурсам
     * @param userId  - идентификатор пользователя
     * @param username - имя пользователя (логин)
     * @param roles - роли пользователя
     * @return - токен доступа
     */
    public String createAccessToken(Integer userId, String username, List<RoleEntity> roles, String uuidToken) {
        Map<String, Object> claims = getClaimsFrom(userId, username, roles, uuidToken);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());
        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    /**
     * Метод создает токен обновления
     * @param userId  - идентификатор пользователя
     * @param username - имя пользователя (логин)
     * @param uuidToken - идентификатор токена
     * @return - токен обновления
     */
    public String createRefreshToken(Integer userId, String username, String uuidToken) {
        Map<String, Object> claims = getClaimsFrom(userId, username, uuidToken);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getRefresh());
        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    /**
     * Метод генерирует токен обновления
     *
     * @param refreshToken - токен
     * @return = новый токен обновления
     * @throws EntityNotFoundException - выбрасывается если пользователь не найден
     */
    public JwtResponse refreshUserTokens(String refreshToken) throws EntityNotFoundException {
        JwtResponse jwtResponse = new JwtResponse();
        if (!validateToken(refreshToken)) {
            throw new AccessDeniedException("Токен обновления невалидный");
        }
        Integer userId = Integer.valueOf(getId(refreshToken));
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id " + userId + " не найден"));
        List<RoleDto> roles = roleListMapper.mapToRoleDtoList(userEntity.getRoles());
        if (!userEntity.getTokenUUID().equals(getUUIDFrom(refreshToken))) {
            throw new AccessDeniedException("Токен обновления невалидный");
        }
        String uuidToken = UUID.randomUUID().toString();
        userEntity.setTokenUUID(uuidToken);
        userRepository.save(userEntity);
        updateToken(jwtResponse, userId, userEntity.getUsername(), roles, userEntity.getRoles(), uuidToken);
        return jwtResponse;
    }

    /**
     * Метод позволяет аутентифицировать пользователя на основе данных из токена.
     *
     * @param token - токен
     * @return - объект аутентификации
     */
    public Authentication getAuthentication(String token) {
        String username = getUsernameFrom(token);
        String uuidFromToken = getUUIDFrom(token);
        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails.getUuidToken().equals(uuidFromToken)) {
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } else {
            return null;
        }
    }

    /**
     * Метод позволяет получить Claim для токена по каждому переданному параметру
     * @param id  - идентификатор пользователя
     * @param username - имя пользователя(логин)
     * @param uuidToken - идентификатор токена
     * @return - список Claim
     */
    private Map<String, Object> getClaimsFrom(Integer id, String username, String uuidToken) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("id", id);
        claims.put("uuid", uuidToken);
        return claims;
    }

    /**
     * Метод позволяет получить Claim для токена по каждому переданному параметру
     * @param userId - идентификатор пользователя
     * @param username - имя пользователя(логин)
     * @param roles - роли пользователя
     * @param uuidToken - идентификатор пользователя
     * @return - словарь Claim
     */
    private Map<String, Object> getClaimsFrom(Integer userId, String username, List<RoleEntity> roles, String uuidToken) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("id", userId);
        claims.put("roles", resolvesRoles(roles));
        claims.put("uuid", uuidToken);
        return claims;
    }

    /**
     * Метод преобразует список сущностей ролей в список названий ролей
     *
     * @param roles - список ролей
     * @return - список названий
     */
    private List<String> resolvesRoles(List<RoleEntity> roles) {
        return roles.stream()
                .map(RoleEntity::getRole)
                .collect(Collectors.toList());
    }

    /**
     * Метод проверяет валидность токена
     *
     * @param token - токен
     * @return = true если токен валиден, иначе false
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return !claims.getPayload().getExpiration().before(new Date());
        } catch (ExpiredJwtException | SignatureException e) {
            return false;
        }
    }

    /**
     * Метод должен вернуть id пользователя(Collection) из токена
     *
     * @param token - токен
     * @return - идентификатор пользователя
     */
    private String getId(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
        return claims.getPayload().get("id", Integer.class).toString();
    }

    /**
     * Метод должен вернуть имя пользователя(логин) из токена
     *
     * @param token - токен
     * @return - имя пользователя
     */
    private String getUsernameFrom(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
        return claims.getPayload().get("username", String.class);
    }

    /**
     * Метод позволяет получить uuid из токена
     *
     * @param token - токен
     * @return - uuid
     */
    private String getUUIDFrom(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
        return claims.getPayload().get("uuid", String.class);
    }

    /**
     * Метод позволяет обновить токен
     * @param jwtResponse - токен ответа
     * @param userId - идентификатор пользователя
     * @param username - логин пользователя
     * @param roles - роли как dto
     * @param rolesEntity - роли как сущности
     * @param uuidToken - идентификатор токена
     */
    private void updateToken(JwtResponse jwtResponse, Integer userId, String username, List<RoleDto> roles, List<RoleEntity> rolesEntity, String uuidToken){
        jwtResponse.setId(userId);
        jwtResponse.setUsername(username);
        jwtResponse.setRoles(roles);
        jwtResponse.setAccessToken(createAccessToken(userId, username, rolesEntity, uuidToken));
        jwtResponse.setRefreshToken(createRefreshToken(userId, username, uuidToken));
    }
}