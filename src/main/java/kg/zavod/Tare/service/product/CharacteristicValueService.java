package kg.zavod.Tare.service.product;

import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForSaveAdminDto;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForUpdateAdminDto;

import java.util.List;

public interface CharacteristicValueService {
    /**
     * Метод позволяет сохранить список значений характеристик для продукта.
     * Используется в админке MVC
     * @param characteristicsValueForSave - список значений характеристик для сохранения
     * @param product - продукт для которого необходимо записать значения характеристик
     * @throws EntitiesNotFoundException - в случае если характеристики не будут найдены
     */
    void saveCharacteristicsValueMvc(List<CharacteristicValueForSaveAdminDto> characteristicsValueForSave, ProductEntity product) throws EntitiesNotFoundException, EntityNotFoundException;

    /**
     * Метод позволяет отредактировать список значений характеристик для продукта.
     * Используется в админке MVC
     * @param characteristicsValueForUpdate - список значений характеристик для редактирования
     * @param product - продукт для которого необходимо отредактировать значения характеристик
     * @throws EntityNotFoundException - в случае если характеристика не будет найдена
     */
    void updateCharacteristicsValueMvc(List<CharacteristicValueForUpdateAdminDto> characteristicsValueForUpdate, ProductEntity product) throws EntityNotFoundException;
}
