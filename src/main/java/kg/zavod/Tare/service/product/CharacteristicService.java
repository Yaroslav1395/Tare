package kg.zavod.Tare.service.product;

import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.exception.StaticDataException;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForAdminDto;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForSaveAdminDto;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForUpdateAdminDto;

import java.util.List;

public interface CharacteristicService {
    /**
     * Метод позволяет получить все характеристики. Используется в админке MVC.
     * @return - список характеристик
     * @throws EntitiesNotFoundException - в случае если характеристики не найдены
     */
    List<CharacteristicForAdminDto> getAllCharacteristicsForAdmin() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить характеристику. Используется в админке MVC.
     * @param characteristic - характеристика для сохранения
     * @throws DuplicateEntityException - в случае если характеристика дублируется
     */
    void saveCharacteristic(CharacteristicForSaveAdminDto characteristic) throws DuplicateEntityException;

    /**
     * Метод позволяет отредактировать характеристику. Используется в админке MVC.
     * @param characteristic - характеристика для редактирования
     * @throws DuplicateEntityException - в случае если характеристика дублируется
     * @throws EntityNotFoundException - в случае если характеристика не найдена
     * @throws StaticDataException - в случае попытки редактирования статичной характеристики
     */
    void updateCharacteristic(CharacteristicForUpdateAdminDto characteristic) throws EntityNotFoundException, DuplicateEntityException, StaticDataException;

    /**
     * Метод позволяет удалять характеристику
     *
     * @param id - id характеристики
     * @throws StaticDataException - в случае если пытаются удалить статичную характеристику
     */
    void deleteCharacteristicById(Integer id) throws StaticDataException;
}
