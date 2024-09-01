package kg.zavod.Tare.mapper.subcategory;

import kg.zavod.Tare.domain.category.SubcategoryEntity;
import kg.zavod.Tare.dto.subcategory.SubcategoryDto;
import kg.zavod.Tare.dto.subcategory.SubcategorySimpleDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = SubcategoryMapper.class)
public interface SubcategoryListMapper {
    List<SubcategorySimpleDto> mapToSubcategorySimpleDto(List<SubcategoryEntity> subcategoryEntityList);
    List<SubcategoryDto> mapToSubcategoryDtoList(List<SubcategoryEntity> subcategories);
}
