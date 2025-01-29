package kg.zavod.Tare.mapper.delivery.capacity;

import kg.zavod.Tare.domain.delivery.CapacityEntity;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForAdminDto;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForSaveAdminDto;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForUpdateAdminDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CapacityMapper {
    /**
     * Метод позволяет преобразовать сущность объема доставки в DTO
     * @param capacity - сущность объема доставки
     * @return - DTO объема доставки
     */
    CapacityForAdminDto mapToCapacityForAdminDto(CapacityEntity capacity);

    /**
     * Метод преобразует DTO объема доставки в сущность при сохранении
     * @param capacityForSaveAdminDto - DTO объема доставки
     * @return - сущность объема доставки
     */
    CapacityEntity mapToCapacityEntity(CapacityForSaveAdminDto capacityForSaveAdminDto);

    /**
     * Метод позволяет отредактировать сущность объема доставки на основе DTO
     * @param capacityForUpdateAdminDto - DTO объема доставки
     * @param capacity - сущность объема доставки
     */
    void updateCapacityEntity(CapacityForUpdateAdminDto capacityForUpdateAdminDto, @MappingTarget CapacityEntity capacity);
}
