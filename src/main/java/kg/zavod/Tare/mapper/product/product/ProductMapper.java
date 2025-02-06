package kg.zavod.Tare.mapper.product.product;

import kg.zavod.Tare.domain.category.SubcategoryEntity;
import kg.zavod.Tare.domain.product.ImageEntity;
import kg.zavod.Tare.domain.product.ProductCharacteristicEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.product.product.mvc.*;
import kg.zavod.Tare.mapper.product.characteristicValue.CharacteristicValueListMapper;
import kg.zavod.Tare.mapper.product.image.ImageListMapper;
import kg.zavod.Tare.mapper.product.image.ImageMapper;
import kg.zavod.Tare.mapper.subcategory.SubcategoryMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SubcategoryMapper.class, CharacteristicValueListMapper.class, ImageListMapper.class, ImageMapper.class})
public interface ProductMapper {
    /**
     * Метод позволяет преобразовать сущность продукта в DTO для клиента MVC
     * @param product - сущность продукта
     * @return - DTO продукта
     */
    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "price", source = "productCharacteristics", qualifiedByName = "getPriceFromCharacteristics")
    @Mapping(target = "packaging", source = "productCharacteristics", qualifiedByName = "getPackagingFromCharacteristics")
    @Mapping(target = "description", source = "product.description")
    @Mapping(target = "shortDescription", source = "product.description", qualifiedByName = "getShortDescription")
    @Mapping(target = "idFromFactoryBd", source = "product.idFromFactoryBd")
    ProductForUserDto mapToProductForUserDto(ProductEntity product);

    /**
     * Метод позволяет преобразовать сущность продукта в DTO.
     * Используется для админки MVC
     * @param product - сущность продукта
     * @return - DTO продукта
     */
    @Mapping(target = "subcategory", source = "product.subcategory.name")
    @Mapping(target = "characteristics", source = "product.productCharacteristics")
    ProductForAdminDto mapToProductForAdminDto(ProductEntity product);

    /**
     * Метод позволяет преобразовать DTO продукта в сущность продукта.
     * @param productForSaveAdminDto - DTO продукта
     * @return - сущность продукта
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "productForSaveAdminDto.name")
    @Mapping(target = "subcategory", source = "subcategory")
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "productCharacteristics", ignore = true)
    ProductEntity mapToProductEntity(ProductForSaveAdminDto productForSaveAdminDto, SubcategoryEntity subcategory);

    /**
     * Метод позволяет отредактировать сущность продукта на основе данных из DTO
     * @param productForUpdateDto - DTO продукта
     * @param subcategory - сущность подкатегории
     * @param productEntity - сущность продукта
     */
    @Mapping(target = "id", source = "productForUpdateDto.id")
    @Mapping(target = "name", source = "productForUpdateDto.name")
    @Mapping(target = "idFromFactoryBd", source = "productForUpdateDto.idFromFactoryBd")
    @Mapping(target = "description", source = "productForUpdateDto.description")
    @Mapping(target = "subcategory", source = "subcategory")
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "productCharacteristics", ignore = true)
    void updateProductEntity(ProductForUpdateAdminDto productForUpdateDto, SubcategoryEntity subcategory, @MappingTarget ProductEntity productEntity);

    /**
     * Метод преобразовывает сущность продукта в DTO для главной страницы MVC
     * @param productEntity - сущность продукта
     * @return - DTO продукта
     */
    @Mapping(target = "id", source = "productEntity.id")
    @Mapping(target = "name", source = "productEntity.name")
    @Mapping(target = "price", source = "productCharacteristics", qualifiedByName = "getPriceFromCharacteristics")
    @Mapping(target = "image", source = "images", qualifiedByName = "getFirstImage")
    ProductForHomeDto mapToProductForHomeDto(ProductEntity productEntity);


    /**
     * Метод позволяет найти цену продукта в характеристиках
     * @param productCharacteristics - характеристики продукта
     * @return - цена
     */
    @Named("getPriceFromCharacteristics")
    default Double getPriceFromCharacteristics(List<ProductCharacteristicEntity> productCharacteristics){
        return productCharacteristics.stream()
                .filter(characteristicValue -> "Цена, сом".equals(characteristicValue.getCharacteristic().getName()))
                .findFirst()
                .map(ProductCharacteristicEntity::getValue)
                .orElse(0D);
    }

    /**
     * Метод позволяет найти цену продукта в характеристиках
     * @param productCharacteristics - характеристики продукта
     * @return - цена
     */
    @Named("getPackagingFromCharacteristics")
    default Double getPackagingFromCharacteristics(List<ProductCharacteristicEntity> productCharacteristics){
        return productCharacteristics.stream()
                .filter(characteristicValue -> "Фасовка, шт".equals(characteristicValue.getCharacteristic().getName()))
                .findFirst()
                .map(ProductCharacteristicEntity::getValue)
                .orElse(0D);
    }

    /**
     * Метод позволяет обрезать описание продукта после второй точки
     * @param description - описание продукта
     * @return - обрезанное описание продукта
     */
    @Named("getShortDescription")
    default String getShortDescription(String description) {
        if (description == null || description.isEmpty()) return description;
        int firstDot = description.indexOf('.');
        if (firstDot == -1)  return description;
        int secondDot = description.indexOf('.', firstDot + 1);
        if (secondDot == -1) return description;
        return description.substring(0, secondDot + 1);
    }

    /**
     * Метод позволяет найти первую картинку продукта, так как на главной странице нужна только одна
     * картинка для отображения
     * @param images - список сущностей картинок продукта
     * @return - первая найденная картинка
     */
    @Named("getFirstImage")
    default ImageEntity getFirstImage(List<ImageEntity> images) {
        if (images != null) {
            return images.stream()
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
