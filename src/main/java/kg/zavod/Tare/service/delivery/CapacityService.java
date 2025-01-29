package kg.zavod.Tare.service.delivery;


import kg.zavod.Tare.dto.deliviry.capacity.CapacityForAdminDto;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForSaveAdminDto;
import kg.zavod.Tare.dto.deliviry.capacity.CapacityForUpdateAdminDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;

import java.util.List;

public interface CapacityService {
    /**
     * Метод позволяет получить все допустимые объемы доставки
     * @throws EntitiesNotFoundException - в случае если ни оного допустимого объема доставки не найдено
     * @return - список допустимых объемов доставки
     */
    List<CapacityForAdminDto> getAllCapacities() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить допустимый объем доставки
     * @param capacityForSaveAdminDto - допустимый объем доставки для сохранения
     * @throws DuplicateEntityException - в случае попытки создания дубликата
     */
    void saveCapacity(CapacityForSaveAdminDto capacityForSaveAdminDto) throws DuplicateEntityException;

    /**
     * Метод позволяет редактировать допустимый объем доставки
     * @param capacityForUpdateAdminDto - допустимый объем доставки для редактирования
     * @throws EntityNotFoundException - в случае если не найден допустимый объем доставки для редактирования
     * @throws DuplicateEntityException - в случае попытки создания дубликата
     */
    void updateCapacity(CapacityForUpdateAdminDto capacityForUpdateAdminDto) throws EntityNotFoundException, DuplicateEntityException;

    /**
     * Метод позволяет удалять допустимый объем доставки
     * @param id - id допустимого объема доставки
     */
    void deleteCapacityById(Integer id);











    /**
     * Метод позволяет получить допустимый объем доставки по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id допустимого объема доставки
     * @return - найденный допустимый объем доставки
     */
    CapacityForAdminDto getCapacityById(Integer id) throws EntityNotFoundException;
}
