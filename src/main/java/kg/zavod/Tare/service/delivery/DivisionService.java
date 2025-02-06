package kg.zavod.Tare.service.delivery;

import kg.zavod.Tare.dto.deliviry.division.DivisionDto;
import kg.zavod.Tare.dto.deliviry.division.mvc.DivisionForAdminDto;
import kg.zavod.Tare.dto.deliviry.division.mvc.DivisionForSaveAdminDto;
import kg.zavod.Tare.dto.deliviry.division.mvc.DivisionForUpdateAdminDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;

import java.util.List;

public interface DivisionService {
    /**
     * Метод позволяет получить все территориальные деления
     * @throws EntitiesNotFoundException - в случае если ни оного территориального деления не найдено
     * @return - список территориальных делений
     */
    List<DivisionForAdminDto> getAllDivisionsForAdmin() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить территориальное деление
     * @param divisionForSaveDto - территориальное деление для сохранения
     * @throws DuplicateEntityException - если территориальное деление существует
     */
    void saveDivision(DivisionForSaveAdminDto divisionForSaveDto) throws DuplicateEntityException;

    /**
     * Метод позволяет редактировать территориальное деление
     * @param divisionForUpdateDto - территориальное деление для редактирования
     * @throws EntityNotFoundException - в случае если не найдено территориальное деление для редактирования
     * @throws DuplicateEntityException - если территориальное деление существует
     */
    void updateDivision(DivisionForUpdateAdminDto divisionForUpdateDto) throws EntityNotFoundException, DuplicateEntityException;

    /**
     * Метод позволяет удалять территориальное деление
     * @param id - id территориального деления
     */
    void deleteDivisionById(Integer id);
}
