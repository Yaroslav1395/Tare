package kg.zavod.Tare.sequrity;

import kg.zavod.Tare.domain.user.RoleEntity;
import kg.zavod.Tare.domain.user.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailFactory {
    public static UserDetailsImpl create(UserEntity user) {
        return UserDetailsImpl.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getName())
                .surname(user.getSurname())
                .patronymic(user.getPatronymic())
                .uuidToken(user.getTokenUUID())
                .authorities(mapToGrantedAuthorities(user.getRoles()))
                .build();
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<RoleEntity> roles) {
        return roles.stream()
                .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getRole()))
                .collect(Collectors.toList());
    }
}
