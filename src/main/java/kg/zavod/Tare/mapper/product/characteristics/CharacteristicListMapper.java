package kg.zavod.Tare.mapper.product.characteristics;

import kg.zavod.Tare.domain.product.CharacteristicEntity;
import kg.zavod.Tare.dto.product.characteristic.CharacteristicDto;
import kg.zavod.Tare.dto.product.characteristic.mvc.CharacteristicForAdminDto;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring", uses = CharacteristicMapper.class)
public interface CharacteristicListMapper {

    /**
     * Метод позволяет преобразовать список сущностей характеристик в список DTO
     * @param characteristics - список сущностей характеристик
     * @return - список DTO характеристик
     */
    List<CharacteristicForAdminDto> mapToCharacteristicDtoListMvc(List<CharacteristicEntity> characteristics);
    List<CharacteristicDto> mapToCharacteristicDtoList(List<CharacteristicEntity> characteristics);
}
