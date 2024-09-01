package kg.zavod.Tare.mapper.subcategory;

import kg.zavod.Tare.domain.category.CategoryEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.domain.category.SubcategoryEntity;
import kg.zavod.Tare.dto.exception.MultipartFileParseException;
import kg.zavod.Tare.dto.subcategory.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SubcategoryMapper {
    SubcategorySimpleDto mapToSubcategorySimpleDto(SubcategoryEntity subcategoryEntity);

    @Mapping(target = "categoryId", source = "subcategoryEntity.category.id")
    @Mapping(target = "productsIds", source = "products", qualifiedByName = "getProductsIdsFrom")
    SubcategoryDto mapToSubcategoryDto(SubcategoryEntity subcategoryEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "categoryId", source = "category.id")
    SubcategoryForProductDto mapToSubcategoryForProductDto(SubcategoryEntity subcategoryEntity);

    @Mapping(target = "subcategoryImage", source = "subcategoryForSaveDto.subcategoryImage", qualifiedByName = "multipartFileToBase64")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "name", source = "subcategoryForSaveDto.name")
    SubcategoryEntity mapToSubcategoryEntity(SubcategoryForSaveDto subcategoryForSaveDto, CategoryEntity category);

    @Mapping(target = "subcategoryImage", source = "subcategoryImage", qualifiedByName = "multipartFileToBase64")
    void updateSubcategoryFromDto(SubcategoryForUpdateDto subcategoryDto, @MappingTarget SubcategoryEntity subcategoryEntity);

    /**
     * Метод позволит получить список id продуктов из подкатегории
     * @param products - подкатегория
     * @return - список id продуктов
     */
    @Named("getProductsIdsFrom")
    default List<Integer> getProductsIdsFrom(List<ProductEntity> products){
        if(products == null) return new ArrayList<>();
        return products.stream()
                .map(ProductEntity::getId)
                .toList();
    }

    /**
     * Метод позволяет преобразовать MultipartFile в строку Base64
     * @param file - картинка как MultipartFile
     * @return - картинка как Base64
     */
    @Named("multipartFileToBase64")
    default String multipartFileToBase64(MultipartFile file) {
        try {
            byte[] fileContent = file.getBytes();
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new MultipartFileParseException("Ошибка при преобразовании MultipartFile в Base64");
        }
    }
}
