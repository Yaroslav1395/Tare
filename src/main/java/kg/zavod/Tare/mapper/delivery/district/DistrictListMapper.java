package kg.zavod.Tare.mapper.delivery.district;

import kg.zavod.Tare.domain.delivery.DistrictEntity;
import kg.zavod.Tare.dto.deliviry.district.DistrictDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = DistrictMapper.class)
public interface DistrictListMapper {
    List<DistrictDto> mapToDistrictDtoList(List<DistrictEntity> districts);
}
