package kg.zavod.Tare.mapper.notice;

import kg.zavod.Tare.domain.NoticeEntity;
import kg.zavod.Tare.dto.notice.NoticeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = NoticeMapper.class)
public interface NoticeListMapper {
    List<NoticeDto> mapToNoticeDtoList(List<NoticeEntity> notices);
}
