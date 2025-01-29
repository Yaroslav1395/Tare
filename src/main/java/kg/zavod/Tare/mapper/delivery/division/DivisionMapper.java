package kg.zavod.Tare.mapper.delivery.division;

import kg.zavod.Tare.domain.delivery.DivisionEntity;
import kg.zavod.Tare.dto.deliviry.division.DivisionDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionForSaveDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionForUpdateDto;
import kg.zavod.Tare.dto.deliviry.division.mvc.DivisionForAdminDto;
import kg.zavod.Tare.dto.deliviry.division.mvc.DivisionForSaveAdminDto;
import kg.zavod.Tare.dto.deliviry.division.mvc.DivisionForUpdateAdminDto;
import kg.zavod.Tare.mapper.delivery.district.DistrictListMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = DistrictListMapper.class)
public interface DivisionMapper {
    /**
     * Метод позволяет преобразовать сущность территориального деления в DTO
     * @param divisionEntity - сущностей территориального деления
     * @return - DTO территориального деления
     */
    DivisionForAdminDto mapToDivisionForAdminDto(DivisionEntity divisionEntity);

    /**
     * Метод позволяет преобразовать DTO территориального деления в сущность
     * @param divisionForSaveDto - DTO территориального деления
     * @return - сущность
     */
    DivisionEntity mapToDivisionEntity(DivisionForSaveAdminDto divisionForSaveDto);

    /**
     * Метод позволяет отредактировать сущность района доставки на основе данных DTO
     * @param divisionForUpdateDto - DTO территориального деления
     * @param divisionEntity - сущностей территориального деления
     */
    void updateDivisionEntity(DivisionForUpdateAdminDto divisionForUpdateDto, @MappingTarget DivisionEntity divisionEntity);



    DivisionDto mapToDivisionDto(DivisionEntity divisionEntity);
}
