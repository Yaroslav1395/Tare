package kg.zavod.Tare.mapper.delivery.capacity;

import kg.zavod.Tare.domain.delivery.CapacityEntity;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityDto;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring", uses = CapacityMapper.class)
public interface CapacityListMapper {
    List<CapacityDto> mapToCapacityListMapper(List<CapacityEntity> capacities);
}
