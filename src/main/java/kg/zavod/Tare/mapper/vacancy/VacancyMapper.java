package kg.zavod.Tare.mapper.vacancy;

import kg.zavod.Tare.domain.VacancyEntity;
import kg.zavod.Tare.dto.vacancy.VacancyForAdminDto;
import kg.zavod.Tare.dto.vacancy.VacancyForSaveAdminDto;
import kg.zavod.Tare.dto.vacancy.VacancyForUpdateAdminDto;
import kg.zavod.Tare.dto.vacancy.VacancyForUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring" )
public interface VacancyMapper {
    /**
     * Метод преобразует сущность вакансии в DTO для клиента
     * @param vacancyEntity - сущность вакансии
     * @return - DTO вакансии
     */
    @Mapping(target = "offer", source = "vacancyEntity.offer", qualifiedByName = "addBrTagToString")
    @Mapping(target = "description", source = "vacancyEntity.description", qualifiedByName = "addBrTagToString")
    VacancyForUserDto mapToVacancyForUserDto(VacancyEntity vacancyEntity);

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

    /**
     * Метод позволяет заменить все знаки новой стоки на HTML теги br
     * @param str - строка для замены
     * @return - строка с HTML тегами
     */
    @Named("addBrTagToString")
    default String addBrTagToString(String str) {
        return str.replace("\n", "<br>").replace("\r", "");
    }
}
