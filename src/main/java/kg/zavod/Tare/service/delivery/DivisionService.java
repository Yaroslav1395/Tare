package kg.zavod.Tare.service.delivery;

import kg.zavod.Tare.dto.deliviry.division.DivisionDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionForSaveDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionForUpdateDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;

import java.util.List;

public interface DivisionService {
    /**
     * Метод позволяет получить территориальное деление по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id территориального деления
     * @return - найденное территориальное деление
     */
    DivisionDto getDivisionById(Integer id) throws EntityNotFoundException;

    /**
     * Метод позволяет получить все территориальные деления
     * @throws EntitiesNotFoundException - в случае если ни оного территориального деления не найдено
     * @return - список территориальных делений
     */
    List<DivisionDto> getAllDivisions() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить территориальное деление
     * @param divisionForSaveDto - территориальное деление для сохранения
     * @throws DuplicateEntityException - если территориальное деление существует
     * @return - сохраненное территориальное деление
     */
    DivisionDto saveDivision(DivisionForSaveDto divisionForSaveDto) throws DuplicateEntityException;

    /**
     * Метод позволяет редактировать территориальное деление
     * @param divisionForUpdateDto - территориальное деление для редактирования
     * @throws EntityNotFoundException - в случае если не найдено территориальное деление для редактирования
     * @throws DuplicateEntityException - если территориальное деление существует
     * @return - отредактированное территориальное деление
     */
    DivisionDto updateDivision(DivisionForUpdateDto divisionForUpdateDto) throws EntityNotFoundException, DuplicateEntityException;

    /**
     * Метод позволяет удалять территориальное деление
     * @param id - id территориального деления
     * @return - удалено или нет
     */
    boolean deleteDivisionById(Integer id);
}
