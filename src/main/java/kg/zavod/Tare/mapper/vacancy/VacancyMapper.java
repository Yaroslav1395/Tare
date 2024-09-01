package kg.zavod.Tare.mapper.vacancy;

import kg.zavod.Tare.domain.VacancyEntity;
import kg.zavod.Tare.dto.vacancy.VacancyDto;
import kg.zavod.Tare.dto.vacancy.VacancyForSaveDto;
import kg.zavod.Tare.dto.vacancy.VacancyForUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring" )
public interface VacancyMapper {
    VacancyDto mapToVacancyDto(VacancyEntity vacancyEntity);

    VacancyEntity mapToVacancyEntity(VacancyForSaveDto vacancyForSaveDto);

    void updateVacancyEntity(VacancyForUpdateDto vacancyForUpdateDto, @MappingTarget VacancyEntity vacancyEntity);
}
