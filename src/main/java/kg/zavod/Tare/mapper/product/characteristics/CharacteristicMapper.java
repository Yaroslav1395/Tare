package kg.zavod.Tare.mapper.product.characteristics;

import kg.zavod.Tare.domain.product.CharacteristicEntity;
import kg.zavod.Tare.dto.product.characteristic.mvc.CharacteristicForAdminDto;
import kg.zavod.Tare.dto.product.characteristic.mvc.CharacteristicForSaveAdminDto;
import kg.zavod.Tare.dto.product.characteristic.mvc.CharacteristicForUpdateAdminDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CharacteristicMapper {

    /**
     * Метод позволяет преобразовать сущность характеристики в DTO. Используется в админке MVC.
     * @param characteristic - сущность характеристики
     * @return - DTO характеристики
     */
    CharacteristicForAdminDto mapCharacteristicToDtoMvc(CharacteristicEntity characteristic);

    /**
     * Метод позволяет преобразовать DTO характеристики в сущность при сохранении.
     * Используется в админке MVC.
     * @param characteristic - DTO характеристики для сохранения
     * @return - сущность характеристики
     */
    CharacteristicEntity mapDtoToCharacteristicMvc(CharacteristicForSaveAdminDto characteristic);

    /**
     * Метод позволяет отредактировать сущность характеристики на основе DTO.
     * Используется в админке MVC.
     * @param characteristicDto - DTO характеристики для редактирования
     * @param characteristic - сущность характеристики для редактирования
     */
    void updateCharacteristicEntityMvc(CharacteristicForUpdateAdminDto characteristicDto, @MappingTarget CharacteristicEntity characteristic);
}
