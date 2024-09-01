package kg.zavod.Tare.mapper.delivery.capacity;

import kg.zavod.Tare.domain.delivery.CapacityEntity;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityDto;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForSaveDto;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CapacityMapper {
    CapacityDto mapToCapacityDto(CapacityEntity capacity);
    CapacityEntity mapToCapacityEntity(CapacityForSaveDto capacityForSaveDto);
    void updateCapacityEntity(CapacityForUpdateDto capacityForUpdateDto, @MappingTarget CapacityEntity capacity);
}
