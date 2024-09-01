package kg.zavod.Tare.service.product;

import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueDto;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForSaveDto;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForSaveWithProductDto;

import java.util.List;

public interface CharacteristicValueService {
    /**
     * Метод позволяет сохранить список значений характеристик
     * @param characteristicsValueForSave - список значений характеристик для сохранения
     * @param product - продукт для которого необходимо записать значения характеристик
     * @return - сохраненные значения характеристик
     * @throws EntitiesNotFoundException - в случае если характеристики не будут найдены
     */
    List<CharacteristicValueDto> saveCharacteristicsValue(List<CharacteristicValueForSaveWithProductDto> characteristicsValueForSave, ProductEntity product) throws EntitiesNotFoundException, EntityNotFoundException;
}
