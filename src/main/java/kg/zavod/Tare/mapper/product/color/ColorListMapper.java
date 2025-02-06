package kg.zavod.Tare.mapper.product.color;

import kg.zavod.Tare.domain.product.ColorEntity;
import kg.zavod.Tare.dto.product.color.ColorForAdminDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ColorMapper.class)
public interface ColorListMapper {

    /**
     * Метод позволяет преобразовать список сущностей цвета в DTO
     * @param colors - список сущностей цвета
     * @return - список DTO цвета
     */
    List<ColorForAdminDto> mapToColorDtoListForMvc(List<ColorEntity> colors);
}
