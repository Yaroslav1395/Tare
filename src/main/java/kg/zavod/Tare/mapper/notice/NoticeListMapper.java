package kg.zavod.Tare.mapper.notice;

import kg.zavod.Tare.domain.NoticeEntity;
import kg.zavod.Tare.dto.notice.NoticeForAdminDto;
import kg.zavod.Tare.dto.notice.NoticeForUserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = NoticeMapper.class)
public interface NoticeListMapper {
    /**
     * Метод позволяет преобразовать список сущностей новостей в DTO для клиента
     * @param notices - список сущностей новостей
     * @return - список DTO новостей
     */
    List<NoticeForUserDto> mapToNoticeForUserDtoList(List<NoticeEntity> notices);

    /**
     * Метод позволяет преобразовать список сущностей новостей в DTO
     * @param notices - список сущностей новостей
     * @return - список DTO новостей
     */
    List<NoticeForAdminDto> mapToNoticeAdminDtoList(List<NoticeEntity> notices);
}
