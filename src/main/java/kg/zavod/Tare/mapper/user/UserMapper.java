package kg.zavod.Tare.mapper.user;

import kg.zavod.Tare.domain.user.UserEntity;
import kg.zavod.Tare.dto.user.UserDto;
import kg.zavod.Tare.dto.user.UserForSaveDto;
import kg.zavod.Tare.dto.user.UserForUpdateDto;
import kg.zavod.Tare.mapper.role.RoleListMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = RoleListMapper.class)
public interface UserMapper {
    UserDto mapToUserDto(UserEntity userEntity);
    UserEntity mapToUserEntity(UserForSaveDto userForSaveDto);
    void updateUserFromDto(UserForUpdateDto dto, @MappingTarget UserEntity entity);
}
