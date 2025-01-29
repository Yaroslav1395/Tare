package kg.zavod.Tare.service.product;

import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.exception.StaticDataException;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicDto;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForSaveDto;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForUpdateDto;
import kg.zavod.Tare.dto.product.characteristic.mvc.CharacteristicForAdminDto;
import kg.zavod.Tare.dto.product.characteristic.mvc.CharacteristicForSaveAdminDto;
import kg.zavod.Tare.dto.product.characteristic.mvc.CharacteristicForUpdateAdminDto;

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
     * Метод позволяет получить характеристику по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id характеристики
     * @return - характеристика
     */
    CharacteristicDto getCharacteristicById(Integer id) throws EntityNotFoundException;

    /**
     * Метод позволяет получить все характеристики
     * @throws EntitiesNotFoundException - в случае если ни оной характеристики не найдено
     * @return - список характеристик
     */
    List<CharacteristicDto> getAllCharacteristics() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить характеристику
     * @param characteristicForSaveDto - характеристика для сохранения
     * @throws DuplicateEntityException - в случае если характеристика с таким названием уже есть
     * @return - сохраненная характеристика
     */
    CharacteristicDto saveCharacteristic(CharacteristicForSaveDto characteristicForSaveDto) throws DuplicateEntityException;

    /**
     * Метод позволят редактировать характеристику
     * @param characteristicForUpdateDto - характеристика для редактирования
     * @throws EntityNotFoundException - в случае если при редактировании не найдена характеристика
     * @throws DuplicateEntityException - в случае если характеристика с таким названием уже есть
     * @throws StaticDataException - в случае если пытаются отредактировать статичную характеристику
     * @return - отредактированная характеристика
     */
    CharacteristicDto updateCharacteristic(CharacteristicForUpdateDto characteristicForUpdateDto) throws EntityNotFoundException, DuplicateEntityException, StaticDataException;

    /**
     * Метод позволяет удалять характеристику
     * @param id - id характеристики
     * @throws StaticDataException - в случае если пытаются удалить статичную характеристику
     * @return - удалена или нет
     */
    Boolean deleteCharacteristicById(Integer id) throws StaticDataException;
}
