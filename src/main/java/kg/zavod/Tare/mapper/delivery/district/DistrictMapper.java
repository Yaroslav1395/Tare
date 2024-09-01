package kg.zavod.Tare.mapper.delivery.district;

import kg.zavod.Tare.domain.delivery.DistrictEntity;
import kg.zavod.Tare.domain.delivery.DivisionEntity;
import kg.zavod.Tare.dto.deliviry.district.DistrictDto;
import kg.zavod.Tare.dto.deliviry.district.DistrictForSaveDto;
import kg.zavod.Tare.dto.deliviry.district.DistrictForTableDto;
import kg.zavod.Tare.dto.deliviry.district.DistrictForUpdateDto;
import kg.zavod.Tare.dto.deliviry.districtCapacityPrice.DeliveryPriceForUpdateDto;
import kg.zavod.Tare.mapper.delivery.price.DeliveryPriceListMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Map;

@Mapper(componentModel = "spring", uses = DeliveryPriceListMapper.class)
public interface DistrictMapper {
    @Mapping(target = "divisionId", source = "district.division.id")
    DistrictDto mapToDistrictDto(DistrictEntity district);
    @Mapping(target = "division", source = "division")
    @Mapping(target = "name", source = "districtForSaveDto.name")
    @Mapping(target = "id", ignore = true)
    DistrictEntity mapToDistrictEntity(DistrictForSaveDto districtForSaveDto, DivisionEntity division);
    @Mapping(target = "district.division", source = "division")
    @Mapping(target = "district.id", ignore = true)
    @Mapping(target = "district.name", source = "districtForUpdateDto.name")
    void updateDistrictEntity(DistrictForUpdateDto districtForUpdateDto, DivisionEntity division, @MappingTarget DistrictEntity district);

    @Mapping(target = "id", source = "district.id")
    @Mapping(target = "districtName", source = "district.name")
    @Mapping(target = "capacityPriceMap", source = "prices")
    DistrictForTableDto mapToDistrictForTableDto(DistrictEntity district, Map<Integer, DeliveryPriceForUpdateDto> prices);
}
