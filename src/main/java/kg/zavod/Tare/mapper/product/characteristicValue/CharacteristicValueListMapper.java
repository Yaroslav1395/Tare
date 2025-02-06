package kg.zavod.Tare.mapper.product.characteristicValue;

import kg.zavod.Tare.domain.product.*;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.characteristicValue.mvc.CharacteristicValueForAdminDto;
import kg.zavod.Tare.dto.product.characteristicValue.mvc.CharacteristicValueForSaveAdminDto;
import kg.zavod.Tare.dto.product.characteristicValue.mvc.CharacteristicValueForUpdateAdminDto;
import kg.zavod.Tare.dto.product.characteristicValue.mvc.CharacteristicValueForUserDto;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", uses = CharacteristicValueMapper.class)
public interface CharacteristicValueListMapper {
    /**
     * Метод позволяет преобразовать список сущностей значений характеристик в список DTO для клиента MVC.
     * @param productCharacteristicsValue - список сущностей значений характеристик
     * @return - список DTO значений характеристик
     */
    List<CharacteristicValueForUserDto> mapToCharacteristicValueForUserDtoList(List<ProductCharacteristicEntity> productCharacteristicsValue);

    /**
     * Метод позволяет преобразовать список сущностей значений характеристик в список DTO.
     * Используется в админке MVC.
     * @param productCharacteristicsValue - список сущностей значений характеристик
     * @return - список DTO значений характеристик
     */
    List<CharacteristicValueForAdminDto> mapToCharacteristicValueForAdminDtoList(List<ProductCharacteristicEntity> productCharacteristicsValue);

    /**
     * Метод позволяет преобразовать список значений характеристик в сущности.
     * Используется в админке MVC
     * @param characteristicValuesForSave - список значений характеристик
     * @param product - продукт
     * @param characteristics - характеристики
     * @param characteristicValueMapper - преобразователь значения характеристики
     * @return - преобразованные значения характеристик в сущности
     * @throws EntityNotFoundException - в случае если подходящие характеристики не будут найдены по id
     */
    default ArrayList<ProductCharacteristicEntity> mapToCharacteristicValueDtoListMvcForSave(List<CharacteristicValueForSaveAdminDto> characteristicValuesForSave, ProductEntity product, Map<Integer, CharacteristicEntity> characteristics, CharacteristicValueMapper characteristicValueMapper) throws EntityNotFoundException {
        if (characteristicValuesForSave == null) {
            return new ArrayList<>();
        }
        ArrayList<ProductCharacteristicEntity> productCharacteristicEntities = new ArrayList<>();
        for (CharacteristicValueForSaveAdminDto characteristicValue : characteristicValuesForSave) {
            productCharacteristicEntities.add(characteristicValueMapper.mapToCharacteristicEntityMvc(characteristicValue, product, characteristics));
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
    default ArrayList<ProductCharacteristicEntity> mapToCharacteristicValueDtoListMvcForUpdate(List<CharacteristicValueForUpdateAdminDto> characteristicValuesForUpdate, ProductEntity product, Map<Integer, CharacteristicEntity> characteristics, CharacteristicValueMapper characteristicValueMapper) throws EntityNotFoundException {
        if (characteristicValuesForUpdate == null) {
            return new ArrayList<>();
        }
        ArrayList<ProductCharacteristicEntity> productCharacteristicEntities = new ArrayList<>();
        for (CharacteristicValueForUpdateAdminDto characteristicValue : characteristicValuesForUpdate) {
            productCharacteristicEntities.add(characteristicValueMapper.mapToCharacteristicEntityForUpdateMvc(characteristicValue, product, characteristics));
        }
        return productCharacteristicEntities;
    }
}
