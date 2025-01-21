package kg.zavod.Tare.service;

import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.vacancy.VacancyForAdminDto;
import kg.zavod.Tare.dto.vacancy.VacancyForSaveAdminDto;
import kg.zavod.Tare.dto.vacancy.VacancyForUpdateAdminDto;
import kg.zavod.Tare.dto.vacancy.VacancyForUserDto;

import java.util.List;


public interface VacancyService {
    /**
     * Метод позволяет получить все вакансии
     * @return - список подкатегорий
     */
    List<VacancyForAdminDto> getAllVacanciesForAdmin();

    /**
     * Метод позволяет получить все вакансии для клиента
     * @return - список подкатегорий
     */
    List<VacancyForUserDto> getAllVacanciesForUser();

    /**
     * Метод позволяет сохранить вакансию
     * @param vacancyForSaveAdminDto - вакансия для сохранения
     */
    void saveVacancy(VacancyForSaveAdminDto vacancyForSaveAdminDto);

    /**
     * Метод позволят редактировать вакансию
     * @param vacancyForUpdateAdminDto - вакансия для редактирования
     * @throws EntityNotFoundException - в случае если не найдена вакансия для редактирования
     */
    void updateVacancy(VacancyForUpdateAdminDto vacancyForUpdateAdminDto) throws EntityNotFoundException;

    /**
     * Метод позволяет удалять вакансию
     * @param id - id подкатегории
     * @return - удалена или нет
     */
    boolean deleteVacancyById(Integer id);






    /**
     * Метод позволяет получить вакансию по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id вакансии
     * @return - найденная вакансия
     */
    VacancyForAdminDto getVacancyById(Integer id) throws EntityNotFoundException;
}
