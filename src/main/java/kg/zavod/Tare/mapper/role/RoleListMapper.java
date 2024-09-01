package kg.zavod.Tare.mapper.role;

import kg.zavod.Tare.domain.user.RoleEntity;
import kg.zavod.Tare.dto.role.RoleDto;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface RoleListMapper {
    List<RoleDto> mapToRoleDtoList(Set<RoleEntity> roleEntities);
    List<RoleDto> mapToRoleDtoList(List<RoleEntity> roleEntities);

    Set<RoleDto> mapToRoleDtoSet(Set<RoleEntity> roleEntities);
}
