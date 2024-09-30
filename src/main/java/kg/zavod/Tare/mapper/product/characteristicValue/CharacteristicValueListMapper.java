package kg.zavod.Tare.mapper.product.characteristicValue;

import kg.zavod.Tare.domain.product.*;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueDto;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForSaveWithProductDto;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForUpdateWithProductDto;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", uses = CharacteristicValueMapper.class)
public interface CharacteristicValueListMapper {
    List<CharacteristicValueDto> mapToCharacteristicValueDtoList(List<ProductCharacteristicEntity> productCharacteristicsValue);

    default ArrayList<ProductCharacteristicEntity> mapToCharacteristicValueDtoListForSave(List<CharacteristicValueForSaveWithProductDto> characteristicValuesForSave, ProductEntity product, Map<Integer, CharacteristicEntity> characteristics, CharacteristicValueMapper characteristicValueMapper) throws EntityNotFoundException {
        if (characteristicValuesForSave == null) {
            return new ArrayList<>();
        }
        ArrayList<ProductCharacteristicEntity> productCharacteristicEntities = new ArrayList<>();
        for (CharacteristicValueForSaveWithProductDto characteristicValue : characteristicValuesForSave) {
            productCharacteristicEntities.add(characteristicValueMapper.mapToCharacteristicEntity(characteristicValue, product, characteristics));
        }
        return productCharacteristicEntities;
    }

    /**
     * Метод необходим для преобразования значений характеристик при их редактировании совместно с продуктом. Используется преобразователь
     * который, обрабатывает ситуацию, когда id равен null. Так как пользователь не только редактирует, но и может добавить новое
     * значение характеристики
     * @param characteristicValuesForUpdate - значения характеристик для преобразования
     * @param product - продукт
     * @param characteristics - словарь характеристик
     * @param characteristicValueMapper - преобразователь значения характеристики
     * @return - преобразованные значения характеристик в сущности
     * @throws EntityNotFoundException - в случае если для значения характеристики не будет найдена характеристика
     */
    default ArrayList<ProductCharacteristicEntity> mapToCharacteristicValueDtoListForUpdate(List<CharacteristicValueForUpdateWithProductDto> characteristicValuesForUpdate, ProductEntity product, Map<Integer, CharacteristicEntity> characteristics, CharacteristicValueMapper characteristicValueMapper) throws EntityNotFoundException {
        if (characteristicValuesForUpdate == null) {
            return new ArrayList<>();
        }
        ArrayList<ProductCharacteristicEntity> productCharacteristicEntities = new ArrayList<>();
        for (CharacteristicValueForUpdateWithProductDto characteristicValue : characteristicValuesForUpdate) {
            productCharacteristicEntities.add(characteristicValueMapper.mapToCharacteristicEntityForUpdateWithProduct(characteristicValue, product, characteristics));
        }
        return productCharacteristicEntities;
    }
}
