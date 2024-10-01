package kg.zavod.Tare.mapper.product.product;

import kg.zavod.Tare.domain.category.SubcategoryEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueDto;
import kg.zavod.Tare.dto.product.image.ImageDto;
import kg.zavod.Tare.dto.product.product.ProductDto;
import kg.zavod.Tare.dto.product.product.ProductForSaveDto;
import kg.zavod.Tare.dto.product.product.ProductForUpdateDto;
import kg.zavod.Tare.mapper.product.characteristicValue.CharacteristicValueListMapper;
import kg.zavod.Tare.mapper.product.image.ImageListMapper;
import kg.zavod.Tare.mapper.subcategory.SubcategoryMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SubcategoryMapper.class, CharacteristicValueListMapper.class, ImageListMapper.class})
public interface ProductMapper {
    @Mapping(target = "id", source = "product.id")
    ProductDto mapToProductDto(ProductEntity product);
    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "productCharacteristics", source = "productCharacteristics")
    @Mapping(target = "images", source = "images")
    ProductDto mapToProductDto(ProductEntity product, List<CharacteristicValueDto> productCharacteristics, List<ImageDto> images);
    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "productCharacteristics", source = "characteristicsValue")
    @Mapping(target = "images", source = "images")
    @Mapping(target = "subcategory", source = "subcategory")
    @Mapping(target = "name", source = "product.name")
    ProductDto mapToProductDto(ProductEntity product, List<ImageDto> images, List<CharacteristicValueDto> characteristicsValue, SubcategoryEntity subcategory);
    @Mapping(target = "subcategory", source = "subcategory")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "images", ignore = true)
    ProductEntity mapToProductEntity(ProductForSaveDto product, SubcategoryEntity subcategory);

    @Mapping(target = "id", source = "productForUpdate.id")
    @Mapping(target = "name", source = "productForUpdate.name")
    @Mapping(target = "idFromFactoryBd", source = "productForUpdate.idFromFactoryBd")
    @Mapping(target = "subcategory", source = "subcategory")
    @Mapping(target = "images", ignore = true)
    ProductEntity updateProduct(ProductForUpdateDto productForUpdate, SubcategoryEntity subcategory);
}
