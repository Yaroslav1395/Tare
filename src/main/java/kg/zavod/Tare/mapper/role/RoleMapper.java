package kg.zavod.Tare.mapper.role;

import kg.zavod.Tare.domain.user.RoleEntity;
import kg.zavod.Tare.dto.role.RoleDto;
import kg.zavod.Tare.dto.role.RoleForSaveDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleEntity mapToRoleEntity(RoleDto roleDto);
    RoleEntity mapToRoleEntity(RoleForSaveDto roleForSaveDto);
    RoleDto mapToRoleDto(RoleEntity roleEntity);
}
