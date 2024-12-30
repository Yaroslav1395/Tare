package kg.zavod.Tare.mapper.subcategory;

import kg.zavod.Tare.domain.category.SubcategoryEntity;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForHomeDto;
import kg.zavod.Tare.mapper.product.product.ProductListMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductListMapper.class)
public interface SubcategoryMapperForMvc {
    /**
     * Метод позволяет преобразовать сущность подкатегории в DTO для главной страницы MVC
     * @param subcategoryEntity - сущность подкатегории
     * @return - DTO подкатегории
     */
    @Mapping(target = "id", source = "subcategoryEntity.id")
    @Mapping(target = "name", source = "subcategoryEntity.name")
    SubcategoryForHomeDto mapToSubcategoryForHomeDto(SubcategoryEntity subcategoryEntity);
}
