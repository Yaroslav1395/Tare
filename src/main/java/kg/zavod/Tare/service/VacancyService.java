package kg.zavod.Tare.service;

import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.subcategory.SubcategoryDto;
import kg.zavod.Tare.dto.subcategory.SubcategoryForSaveDto;
import kg.zavod.Tare.dto.subcategory.SubcategoryForUpdateDto;
import kg.zavod.Tare.dto.vacancy.VacancyDto;
import kg.zavod.Tare.dto.vacancy.VacancyForSaveDto;
import kg.zavod.Tare.dto.vacancy.VacancyForUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface VacancyService {
    /**
     * Метод позволяет получить вакансию по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id вакансии
     * @return - найденная вакансия
     */
    VacancyDto getVacancyById(Integer id) throws EntityNotFoundException;

    /**
     * Метод позволяет получить все вакансии
     * @throws EntitiesNotFoundException - в случае если ни оной вакансии не найдено
     * @return - список подкатегорий
     */
    List<VacancyDto> getAllVacancies() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить вакансию
     * @param vacancyForSaveDto - вакансия для сохранения
     * @return - сохраненная вакансия
     */
    VacancyDto saveVacancy(VacancyForSaveDto vacancyForSaveDto);

    /**
     * Метод позволят редактировать вакансию
     * @param vacancyForUpdateDto - вакансия для редактирования
     * @throws EntityNotFoundException - в случае если не найдена вакансия для редактирования
     * @return - отредактированная вакансия
     */
    VacancyDto updateVacancy(VacancyForUpdateDto vacancyForUpdateDto) throws EntityNotFoundException;

    /**
     * Метод позволяет удалять вакансию
     * @param id - id подкатегории
     * @return - удалена или нет
     */
    boolean deleteVacancyById(Integer id);
}
