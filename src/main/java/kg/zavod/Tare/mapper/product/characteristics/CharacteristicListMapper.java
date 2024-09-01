package kg.zavod.Tare.mapper.product.characteristics;

import kg.zavod.Tare.domain.product.CharacteristicEntity;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicDto;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring", uses = CharacteristicMapper.class)
public interface CharacteristicListMapper {
    List<CharacteristicDto> mapToCharacteristicDtoList(List<CharacteristicEntity> characteristics);
}
