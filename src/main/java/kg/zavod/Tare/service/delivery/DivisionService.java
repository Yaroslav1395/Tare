package kg.zavod.Tare.service.delivery;

import kg.zavod.Tare.dto.deliviry.division.DivisionDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionForSaveDto;
import kg.zavod.Tare.dto.deliviry.division.DivisionForUpdateDto;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;

import java.util.List;

public interface DivisionService {
    /**
     * Метод позволяет получить териториальное деление по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id териториального деления
     * @return - найденное териториальное деление
     */
    DivisionDto getDivisionById(Integer id) throws EntityNotFoundException;

    /**
     * Метод позволяет получить все териториальные деления
     * @throws EntitiesNotFoundException - в случае если ни оного териториального деления не найдено
     * @return - список териториальных делений
     */
    List<DivisionDto> getAllDivisions() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить териториальное деление
     * @param divisionForSaveDto - териториальное деление для сохранения
     * @return - сохраненное териториальное деление
     */
    DivisionDto saveDivision(DivisionForSaveDto divisionForSaveDto);

    /**
     * Метод позволяет редактировать териториальное деление
     * @param divisionForUpdateDto - териториальное деление для редактирования
     * @throws EntityNotFoundException - в случае если не найдено териториальное деление для редактирования
     * @return - отредактированное териториальное деление
     */
    DivisionDto updateDivision(DivisionForUpdateDto divisionForUpdateDto) throws EntityNotFoundException;

    /**
     * Метод позволяет удалять териториальное деление
     * @param id - id териториального деления
     * @return - удалено или нет
     */
    boolean deleteDivisionById(Integer id);
}
