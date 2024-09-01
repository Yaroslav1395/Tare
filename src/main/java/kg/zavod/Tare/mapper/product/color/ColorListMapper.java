package kg.zavod.Tare.mapper.product.color;

import kg.zavod.Tare.domain.product.ColorEntity;
import kg.zavod.Tare.dto.product.color.ColorDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ColorMapper.class)
public interface ColorListMapper {
    List<ColorDto> mapToColorDtoList(List<ColorEntity> colors);
}
