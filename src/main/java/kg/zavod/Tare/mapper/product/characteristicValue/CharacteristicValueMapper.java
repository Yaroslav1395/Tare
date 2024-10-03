package kg.zavod.Tare.mapper.product.characteristicValue;

import kg.zavod.Tare.domain.product.CharacteristicEntity;
import kg.zavod.Tare.domain.product.ColorEntity;
import kg.zavod.Tare.domain.product.ProductCharacteristicEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueDto;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForSaveDto;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForSaveWithProductDto;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForUpdateWithProductDto;
import kg.zavod.Tare.dto.product.image.ImageForSaveDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface CharacteristicValueMapper {
    @Mapping(target = "id", source = "productCharacteristic.id")
    @Mapping(target = "characteristicName", source = "productCharacteristic.characteristic.name")
    CharacteristicValueDto mapToCharacteristicValueDto(ProductCharacteristicEntity productCharacteristic);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "value", source = "value")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "characteristic", source = "characteristic")
    ProductCharacteristicEntity mapToCharacteristicEntity(Integer value, ProductEntity product, CharacteristicEntity characteristic);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "value", source = "characteristicValue.value")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "characteristic", expression = "java(getCharacteristicFrom(characteristicValue, characteristics))")
    ProductCharacteristicEntity mapToCharacteristicEntity(CharacteristicValueForSaveWithProductDto characteristicValue, ProductEntity product, Map<Integer, CharacteristicEntity> characteristics) throws EntityNotFoundException;

    @Mapping(target = "id", expression = "java(getIdFrom(characteristicValue))")
    @Mapping(target = "value", source = "characteristicValue.value")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "characteristic", expression = "java(getCharacteristicFromOnUpdate(characteristicValue, characteristics))")
    ProductCharacteristicEntity mapToCharacteristicEntityForUpdateWithProduct(CharacteristicValueForUpdateWithProductDto characteristicValue, ProductEntity product, Map<Integer, CharacteristicEntity> characteristics) throws EntityNotFoundException;

    /**
     * Метод позволяет найти нужную характеристику для значения характеристики из словаря характеристик
     * @param characteristicValue - значение характеристики для сохранения
     * @param characteristics - словарь характеристик
     * @return - найденная характеристика
     * @throws EntityNotFoundException - в случае если характеристика не найдена
     */
    default CharacteristicEntity getCharacteristicFrom(CharacteristicValueForSaveWithProductDto characteristicValue, Map<Integer, CharacteristicEntity> characteristics) throws EntityNotFoundException {
        CharacteristicEntity characteristic = characteristics.get(characteristicValue.getCharacteristicId());
        if(characteristic == null) throw new EntityNotFoundException("По id " + characteristicValue.getValue() + " не найдена характеристика");
        return characteristic;
    }

    /**
     * Метод позволяет найти нужную характеристику для значения характеристики из словаря характеристик
     * @param characteristicValue - значение характеристики для сохранения
     * @param characteristics - словарь характеристик
     * @return - найденная характеристика
     * @throws EntityNotFoundException - в случае если характеристика не найдена
     */
    default CharacteristicEntity getCharacteristicFromOnUpdate(CharacteristicValueForUpdateWithProductDto characteristicValue, Map<Integer, CharacteristicEntity> characteristics) throws EntityNotFoundException {
        CharacteristicEntity characteristic = characteristics.get(characteristicValue.getCharacteristicId());
        if(characteristic == null) throw new EntityNotFoundException("По id " + characteristicValue.getCharacteristicId() + " не найдена характеристика");
        return characteristic;
    }

    /**
     * Метод позволяет получить id значения характеристики при редактировании совместно с продуктом.
     * Из-за того, что при редактировании совместно с продуктом пользователь может создать новое значение,
     * используется данный метод чтобы, избежать 500
     * @param characteristicValue - значения характеристики
     * @return - id характеристики
     */
    default Integer getIdFrom(CharacteristicValueForUpdateWithProductDto characteristicValue){
        return characteristicValue.getId();
    }
}
