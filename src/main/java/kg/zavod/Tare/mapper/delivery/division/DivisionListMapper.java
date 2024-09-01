package kg.zavod.Tare.mapper.delivery.division;

import kg.zavod.Tare.domain.delivery.DivisionEntity;
import kg.zavod.Tare.dto.deliviry.division.DivisionDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = DivisionMapper.class)
public interface DivisionListMapper {
    List<DivisionDto> mapToDivisionListDto(List<DivisionEntity> divisions);
}
