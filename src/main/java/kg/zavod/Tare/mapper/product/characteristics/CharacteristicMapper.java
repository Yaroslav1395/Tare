package kg.zavod.Tare.mapper.product.characteristics;

import kg.zavod.Tare.domain.product.CharacteristicEntity;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicDto;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForSaveDto;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicForUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CharacteristicMapper {
    CharacteristicDto mapToCharacteristicDto(CharacteristicEntity characteristic);
    CharacteristicEntity mapToCharacteristicEntity(CharacteristicForSaveDto characteristicForSaveDto);
    void updateCharacteristicEntity(CharacteristicForUpdateDto characteristicForUpdateDto, @MappingTarget CharacteristicEntity characteristic);
}
