package kg.zavod.Tare.service.product;

import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicDto;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForSaveDto;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForUpdateDto;

import java.util.List;

public interface CharacteristicService {
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
     * @return - сохраненная характеристика
     */
    CharacteristicDto saveCharacteristic(CharacteristicForSaveDto characteristicForSaveDto);

    /**
     * Метод позволят редактировать характеристику
     * @param characteristicForUpdateDto - характеристика для редактирования
     * @throws EntityNotFoundException - в случае если при редактировании не найдена характеристика
     * @return - отредактированная характеристика
     */
    CharacteristicDto updateCharacteristic(CharacteristicForUpdateDto characteristicForUpdateDto) throws EntityNotFoundException;

    /**
     * Метод позволяет удалять характеристику
     * @param id - id характеристики
     * @return - удалена или нет
     */
    Boolean deleteCharacteristicById(Integer id);
}
