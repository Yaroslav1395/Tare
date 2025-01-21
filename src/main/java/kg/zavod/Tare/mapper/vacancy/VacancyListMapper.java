package kg.zavod.Tare.mapper.vacancy;

import kg.zavod.Tare.domain.VacancyEntity;
import kg.zavod.Tare.dto.vacancy.VacancyForAdminDto;
import kg.zavod.Tare.dto.vacancy.VacancyForUserDto;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring", uses = VacancyMapper.class)
public interface VacancyListMapper {
    /**
     * Метод преобразовывает список сущностей вакансий в список DTO для админки
     * @param vacancyEntityList - список сущностей вакансий
     * @return - список DTO
     */
    List<VacancyForAdminDto> mapToVacancyDtoList(List<VacancyEntity> vacancyEntityList);

    /**
     * Метод преобразует список сущностей вакансий в список DTO для клиента
     * @param vacancyEntityList - список сущностей вакансий
     * @return - список DTO
     */
    List<VacancyForUserDto> mapToVacancyForUserDtoList(List<VacancyEntity> vacancyEntityList);
}
