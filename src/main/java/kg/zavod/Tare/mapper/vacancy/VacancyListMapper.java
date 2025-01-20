package kg.zavod.Tare.mapper.vacancy;

import kg.zavod.Tare.domain.VacancyEntity;
import kg.zavod.Tare.dto.vacancy.VacancyForAdminDto;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring", uses = VacancyMapper.class)
public interface VacancyListMapper {
    List<VacancyForAdminDto> mapToVacancyDtoList(List<VacancyEntity> vacancyEntityList);
}
