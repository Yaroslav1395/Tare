package kg.zavod.Tare.mapper.product.color;

import kg.zavod.Tare.domain.product.ColorEntity;
import kg.zavod.Tare.dto.product.color.ColorForAdminDto;
import kg.zavod.Tare.dto.product.color.ColorForSaveAdminDto;
import kg.zavod.Tare.dto.product.color.ColorForUpdateAdminDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ColorMapper {
    /**
     * Метод позволяет преобразовать сущность цвета в DTO
     * @param colorEntity - сущность цвета
     * @return - DTO цвета
     */
    ColorForAdminDto mapToColorDtoForMvc(ColorEntity colorEntity);

    /**
     * Метод позволяет преобразовать DTO цвета в сущность. Используется в админке MVC.
     * @param colorForSaveAdminDto - DTO цвета
     * @return - сущность цвета
     */
    ColorEntity mapToColorEntityForMvc(ColorForSaveAdminDto colorForSaveAdminDto);

    /**
     * Метод позволяет отредактировать сущность цвета на основе DTO. Используется в админке MVC.
     * @param colorForUpdateDto - DTO цвета
     */
    void updateColorEntityForMvc(ColorForUpdateAdminDto colorForUpdateDto, @MappingTarget ColorEntity colorEntity);
}
