package kg.zavod.Tare.mapper.product.characteristicValue;

import kg.zavod.Tare.domain.product.*;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueDto;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForSaveDto;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueForSaveWithProductDto;
import kg.zavod.Tare.dto.product.image.ImageForSaveDto;
import kg.zavod.Tare.mapper.product.image.ImageMapper;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", uses = CharacteristicValueMapper.class)
public interface CharacteristicValueListMapper {
    List<CharacteristicValueDto> mapToCharacteristicValueDtoList(List<ProductCharacteristicEntity> productCharacteristicsValue);

    default ArrayList<ProductCharacteristicEntity> mapToCharacteristicValueDtoList(List<CharacteristicValueForSaveWithProductDto> characteristicValuesForSave, ProductEntity product, Map<Integer, CharacteristicEntity> characteristics, CharacteristicValueMapper characteristicValueMapper) throws EntityNotFoundException {
        if (characteristicValuesForSave == null) {
            return new ArrayList<>();
        }
        ArrayList<ProductCharacteristicEntity> productCharacteristicEntities = new ArrayList<>();
        for (CharacteristicValueForSaveWithProductDto characteristicValue : characteristicValuesForSave) {
            productCharacteristicEntities.add(characteristicValueMapper.mapToCharacteristicEntity(characteristicValue, product, characteristics));
        }
        return productCharacteristicEntities;
    }
}
