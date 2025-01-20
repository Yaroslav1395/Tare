package kg.zavod.Tare.mapper.vacancy;

import kg.zavod.Tare.domain.VacancyEntity;
import kg.zavod.Tare.dto.vacancy.VacancyForAdminDto;
import kg.zavod.Tare.dto.vacancy.VacancyForSaveAdminDto;
import kg.zavod.Tare.dto.vacancy.VacancyForUpdateAdminDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring" )
public interface VacancyMapper {
    /**
     * Метод позволяет преобразовать сущность вакансии в DTO для админки
     * @param vacancyEntity - сущность вакансии
     * @return - DTO вакансии
     */
    VacancyForAdminDto mapToVacancyDto(VacancyEntity vacancyEntity);

    /**
     * Метод позволяет преобразовать при сохранении DTO вакансии в сущность
     * @param vacancyForSaveAdminDto - вакансия для сохранения
     * @return - сущность вакансии
     */
    VacancyEntity mapToVacancyEntity(VacancyForSaveAdminDto vacancyForSaveAdminDto);

    /**
     * Метод позволяет отредактировать сущность вакансии на основе DTO
     * @param vacancyForUpdateAdminDto - DTO вакансии
     * @param vacancyEntity - сущность вакансии
     */
    void updateVacancyEntity(VacancyForUpdateAdminDto vacancyForUpdateAdminDto, @MappingTarget VacancyEntity vacancyEntity);
}
