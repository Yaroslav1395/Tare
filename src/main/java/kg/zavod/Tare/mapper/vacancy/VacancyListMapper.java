package kg.zavod.Tare.mapper.vacancy;

import kg.zavod.Tare.domain.VacancyEntity;
import kg.zavod.Tare.dto.vacancy.VacancyDto;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring", uses = VacancyMapper.class)
public interface VacancyListMapper {
    List<VacancyDto> mapToVacancyDtoList(List<VacancyEntity> vacancyEntityList);
}
