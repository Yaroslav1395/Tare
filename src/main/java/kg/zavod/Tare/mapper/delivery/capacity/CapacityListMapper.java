package kg.zavod.Tare.mapper.delivery.capacity;

import kg.zavod.Tare.domain.delivery.CapacityEntity;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForAdminDto;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring", uses = CapacityMapper.class)
public interface CapacityListMapper {
    /**
     * Метод позволяет преобразовать сущности объема доставки в DTO
     * @param capacities - список сущностей объема доставки
     * @return - список DTO объема доставки
     */
    List<CapacityForAdminDto> mapToCapacityForAdminDtoList(List<CapacityEntity> capacities);
}
