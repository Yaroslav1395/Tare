package kg.zavod.Tare.service.delivery;


import kg.zavod.Tare.dto.deliviry.capacity.CapacityDto;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForSaveDto;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForUpdateDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;

import java.util.List;

public interface CapacityService {
    /**
     * Метод позволяет получить допустимый объем доставки по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id допустимого объема доставки
     * @return - найденный допустимый объем доставки
     */
    CapacityDto getCapacityById(Integer id) throws EntityNotFoundException;

    /**
     * Метод позволяет получить все допустимые объемы доставки
     * @throws EntitiesNotFoundException - в случае если ни оного допустимого объема доставки не найдено
     * @return - список допустимых объемов доставки
     */
    List<CapacityDto> getAllCapacities() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить допустимый объем доставки
     * @param capacityForSaveDto - допустимый объем доставки для сохранения
     * @throws DuplicateEntityException - в случае попытки создания дубликата
     * @return - сохраненный допустимый объем доставки
     */
    CapacityDto saveCapacity(CapacityForSaveDto capacityForSaveDto) throws DuplicateEntityException;

    /**
     * Метод позволяет редактировать допустимый объем доставки
     * @param capacityForUpdateDto - допустимый объем доставки для редактирования
     * @throws EntityNotFoundException - в случае если не найден допустимый объем доставки для редактирования
     * @throws DuplicateEntityException - в случае попытки создания дубликата
     * @return - отредактированное допустимый объем доставки
     */
    CapacityDto updateCapacity(CapacityForUpdateDto capacityForUpdateDto) throws EntityNotFoundException, DuplicateEntityException;

    /**
     * Метод позволяет удалять допустимый объем доставки
     * @param id - id допустимого объема доставки
     * @return - удален или нет
     */
    boolean deleteCapacityById(Integer id);
}
