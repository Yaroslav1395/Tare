package kg.zavod.Tare.mapper.delivery.division;

import kg.zavod.Tare.domain.delivery.DivisionEntity;
import kg.zavod.Tare.dto.deliviry.division.DivisionDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionForSaveDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionForUpdateDto;
import kg.zavod.Tare.mapper.delivery.district.DistrictListMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = DistrictListMapper.class)
public interface DivisionMapper {
    DivisionDto mapToDivisionDto(DivisionEntity divisionEntity);
    DivisionEntity mapToDivisionEntity(DivisionForSaveDto divisionForSaveDto);
    void updateDivisionEntity(DivisionForUpdateDto divisionForUpdateDto, @MappingTarget DivisionEntity divisionEntity);
}
