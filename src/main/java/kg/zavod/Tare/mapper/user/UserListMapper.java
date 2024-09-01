package kg.zavod.Tare.mapper.user;

import kg.zavod.Tare.domain.user.UserEntity;
import kg.zavod.Tare.dto.user.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface UserListMapper {
    List<UserDto> mapToUserDtoList(List<UserEntity> userEntityList);
    List<UserEntity> mapToUserEntityList(List<UserDto> userDtoList);
}
