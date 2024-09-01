package kg.zavod.Tare.mapper.product.color;

import kg.zavod.Tare.domain.product.ColorEntity;
import kg.zavod.Tare.dto.product.color.ColorDto;
import kg.zavod.Tare.dto.product.color.ColorForSaveDto;
import kg.zavod.Tare.dto.product.color.ColorForUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ColorMapper {
    ColorDto mapToColorDto(ColorEntity colorEntity);
    ColorEntity mapToColorEntity(ColorForSaveDto color);
    void updateColorEntity(ColorForUpdateDto colorForUpdateDto, @MappingTarget ColorEntity colorEntity);
}
