package kg.zavod.Tare.mapper.delivery.district;

import kg.zavod.Tare.domain.delivery.DistrictEntity;
import kg.zavod.Tare.domain.delivery.DivisionEntity;
import kg.zavod.Tare.dto.deliviry.district.DistrictDto;
import kg.zavod.Tare.dto.deliviry.district.DistrictForSaveDto;
import kg.zavod.Tare.dto.deliviry.district.DistrictForTableDto;
import kg.zavod.Tare.dto.deliviry.district.DistrictForUpdateDto;
import kg.zavod.Tare.dto.deliviry.district.mvc.DistrictForAdminDto;
import kg.zavod.Tare.dto.deliviry.district.mvc.DistrictForSaveAdminDto;
import kg.zavod.Tare.dto.deliviry.district.mvc.DistrictForUpdateAdminDto;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceForUpdateDto;
import kg.zavod.Tare.mapper.delivery.price.DeliveryPriceListMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Map;

@Mapper(componentModel = "spring", uses = DeliveryPriceListMapper.class)
public interface DistrictMapper {

    /**
     * Метод позволяет преобразовать сущность района в DTO
     * @param district - сущность района
     * @return - DTO района
     */
    @Mapping(target = "divisionId", source = "district.division.id")
    @Mapping(target = "divisionName", source = "division.name")
    DistrictForAdminDto mapToDistrictForAdminDto(DistrictEntity district);

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







    @Mapping(target = "divisionId", source = "district.division.id")
    DistrictDto mapToDistrictDto(DistrictEntity district);

    @Mapping(target = "id", source = "district.id")
    @Mapping(target = "districtName", source = "district.name")
    @Mapping(target = "capacityPriceMap", source = "prices")
    DistrictForTableDto mapToDistrictForTableDto(DistrictEntity district, Map<Integer, DeliveryPriceForUpdateDto> prices);
}
