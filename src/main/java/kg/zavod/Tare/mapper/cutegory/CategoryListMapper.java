package kg.zavod.Tare.mapper.cutegory;

import kg.zavod.Tare.domain.category.CategoryEntity;
import kg.zavod.Tare.dto.category.CategoryDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface CategoryListMapper {
    List<CategoryDto> mapToCategoryDtoList(List<CategoryEntity> categoryEntityList);
}
