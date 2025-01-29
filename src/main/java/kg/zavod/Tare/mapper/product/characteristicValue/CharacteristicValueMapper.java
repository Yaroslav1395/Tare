package kg.zavod.Tare.mapper.product.characteristicValue;

import kg.zavod.Tare.domain.product.CharacteristicEntity;
import kg.zavod.Tare.domain.product.ColorEntity;
import kg.zavod.Tare.domain.product.ProductCharacteristicEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.characteristic.mvc.CharacteristicForAdminDto;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueDto;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForSaveDto;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForSaveWithProductDto;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForUpdateWithProductDto;
import kg.zavod.Tare.dto.product.characteristicValue.mvc.CharacteristicValueForAdminDto;
import kg.zavod.Tare.dto.product.characteristicValue.mvc.CharacteristicValueForSaveAdminDto;
import kg.zavod.Tare.dto.product.characteristicValue.mvc.CharacteristicValueForUpdateAdminDto;
import kg.zavod.Tare.dto.product.characteristicValue.mvc.CharacteristicValueForUserDto;
import kg.zavod.Tare.dto.product.image.ImageForSaveDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface CharacteristicValueMapper {

    /**
     * Метод позволяет преобразовать сущность значения характеристики в DTO.
     * @param characteristicValue - сущность значения характеристики
     * @return - DTO значения характеристики
     */
    @Mapping(target = "characteristicName", source = "characteristicValue.characteristic.name")
    @Mapping(target = "value", source = "characteristicValue.value")
    CharacteristicValueForUserDto mapToCharacteristicValueForUserDto(ProductCharacteristicEntity characteristicValue);

    /**
     * Метод позволяет преобразовать DTO значения характеристики в сущность.
     * Используется в админке mvc.
     * @param characteristicValue - значение характеристики для сохранения
     * @param product - продукт
     * @param characteristics - словарь характеристик
     * @return - сущность значения характеристики
     * @throws EntityNotFoundException - в случае если в словаре характеристик не найдено подходящего значения по id
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "value",  expression = "java(parseCharacteristicValueToDouble(characteristicValue))")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "characteristic", expression = "java(getCharacteristicFromMvc(characteristicValue, characteristics))")
    ProductCharacteristicEntity mapToCharacteristicEntityMvc(CharacteristicValueForSaveAdminDto characteristicValue, ProductEntity product, Map<Integer, CharacteristicEntity> characteristics) throws EntityNotFoundException;

    /**
     * Метод позволяет преобразовать DTO значения характеристики в сущность.
     * Используется при редактировании значений характеристик в админке mvc.
     * @param characteristicValue - значение характеристики для редактирования
     * @param product - продукт
     * @param characteristics - словарь характеристик
     * @return - сущность значения характеристики
     * @throws EntityNotFoundException - в случае если в словаре характеристик не найдено подходящего значения по id
     */
    @Mapping(target = "id", expression = "java(getIdFromMvc(characteristicValue))")
    @Mapping(target = "value", expression = "java(parseCharacteristicValueToDoubleOnUpdate(characteristicValue))")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "characteristic", expression = "java(getCharacteristicFromOnUpdateMvc(characteristicValue, characteristics))")
    ProductCharacteristicEntity mapToCharacteristicEntityForUpdateMvc(CharacteristicValueForUpdateAdminDto characteristicValue, ProductEntity product, Map<Integer, CharacteristicEntity> characteristics) throws EntityNotFoundException;

    /**
     * Метод позволяет преобразовать сущность значения характеристики в DTO.
     * Используется в админке mvc.
     * @param productCharacteristic - сущность значения характеристики
     * @return - DTO значения характеристики
     */
    @Mapping(target = "id", source = "productCharacteristic.id")
    @Mapping(target = "value", source = "productCharacteristic.value")
    @Mapping(target = "characteristicId", source = "productCharacteristic.characteristic.id")
    CharacteristicValueForAdminDto mapToCharacteristicValueForAdminDto(ProductCharacteristicEntity productCharacteristic);

    /**
     * Метод позволяет найти нужную характеристику для значения характеристики из словаря характеристик.
     * Используется в админке mvc.
     * @param characteristicValue - значение характеристики для сохранения
     * @param characteristics - словарь характеристик
     * @return - найденная характеристика
     * @throws EntityNotFoundException - в случае если характеристика не найдена
     */
    default CharacteristicEntity getCharacteristicFromMvc(CharacteristicValueForSaveAdminDto characteristicValue, Map<Integer, CharacteristicEntity> characteristics) throws EntityNotFoundException {
        CharacteristicEntity characteristic = characteristics.get(characteristicValue.getCharacteristicId());
        if(characteristic == null) throw new EntityNotFoundException("По id " + characteristicValue.getValue() + " не найдена характеристика");
        return characteristic;
    }

    /**
     * Метод позволяет преобразовать значение характеристики из строки в дробное число
     * @param characteristicValue - значение характеристики
     * @return - дробное число
     */
    default Double parseCharacteristicValueToDouble(CharacteristicValueForSaveAdminDto characteristicValue) {
        return Double.parseDouble(characteristicValue.getValue());
    }

    /**
     * Метод позволяет преобразовать значение характеристики из строки в дробное число при редактировании
     * @param characteristicValue - значение характеристики
     * @return - дробное число
     */
    default Double parseCharacteristicValueToDoubleOnUpdate(CharacteristicValueForUpdateAdminDto characteristicValue) {
        return Double.parseDouble(characteristicValue.getValue());
    }

    /**
     * Метод позволяет найти нужную характеристику для значения характеристики из словаря характеристик
     * @param characteristicValue - значение характеристики для сохранения
     * @param characteristics - словарь характеристик
     * @return - найденная характеристика
     * @throws EntityNotFoundException - в случае если характеристика не найдена
     */
    default CharacteristicEntity getCharacteristicFromOnUpdateMvc(CharacteristicValueForUpdateAdminDto characteristicValue, Map<Integer, CharacteristicEntity> characteristics) throws EntityNotFoundException {
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
    default Integer getIdFromMvc(CharacteristicValueForUpdateAdminDto characteristicValue){
        return characteristicValue.getId();
    }














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
