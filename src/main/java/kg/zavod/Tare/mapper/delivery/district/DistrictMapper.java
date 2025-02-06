package kg.zavod.Tare.mapper.delivery.district;

import kg.zavod.Tare.domain.delivery.DistrictEntity;
import kg.zavod.Tare.domain.delivery.DivisionEntity;
import kg.zavod.Tare.dto.deliviry.district.DistrictForTableDto;
import kg.zavod.Tare.dto.deliviry.district.mvc.DistrictForSaveAdminDto;
import kg.zavod.Tare.dto.deliviry.district.mvc.DistrictForUpdateAdminDto;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceForUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface DistrictMapper {

    /**
     * Метод позволяет преобразовать DTO района в сущность
     * @param districtForSaveDto - DTO района
     * @param division - сущность района
     * @return - сущность района
     */
    @Mapping(target = "division", source = "division")
    @Mapping(target = "name", source = "districtForSaveDto.name")
    @Mapping(target = "id", ignore = true)
    DistrictEntity mapToDistrictEntity(DistrictForSaveAdminDto districtForSaveDto, DivisionEntity division);

    /**
     * Метод позволяет отредактировать сущность района на основе данных DTO
     * @param districtForUpdateDto - DTO для редактирования
     * @param division - сущность области
     * @param district - сущность района
     */
    @Mapping(target = "district.division", source = "division")
    @Mapping(target = "district.id", ignore = true)
    @Mapping(target = "district.name", source = "districtForUpdateDto.name")
    void updateDistrictEntity(DistrictForUpdateAdminDto districtForUpdateDto, DivisionEntity division, @MappingTarget DistrictEntity district);

    @Mapping(target = "id", source = "district.id")
    @Mapping(target = "districtName", source = "district.name")
    @Mapping(target = "capacityPriceMap", source = "prices")
    DistrictForTableDto mapToDistrictForTableDto(DistrictEntity district, Map<Integer, DeliveryPriceForUpdateDto> prices);
}
