package kg.zavod.Tare.mapper.subcategory;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.category.CategoryEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.domain.category.SubcategoryEntity;
import kg.zavod.Tare.dto.exception.MultipartFileParseException;
import kg.zavod.Tare.dto.subcategory.*;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForAdminDto;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForSaveAdminDto;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForUpdateAdminDto;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForUserDto;
import kg.zavod.Tare.mapper.product.product.ProductListMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Mapper(componentModel = "spring", uses = ProductListMapper.class)
public interface SubcategoryMapper {
    /**
     * Метод позволяет преобразовать сущность подкатегории в DTO. Используется в MVC на страницы подкатегорий
     * @param subcategoryEntity - сущность подкатегории
     * @return - DTO подкатегории
     */
    @Mapping(target = "id", source = "subcategoryEntity.id")
    @Mapping(target = "name", source = "subcategoryEntity.name")
    @Mapping(target = "categoryName", source = "subcategoryEntity.category.name")
    @Mapping(target = "categoryId", source = "subcategoryEntity.category.id")
    SubcategoryForUserDto mapToSubcategoryForUserDto(SubcategoryEntity subcategoryEntity);

    /**
     * Метод позволяет преобразовать сущность подкатегории в DTO для админки. Используется в MVC
     * @param subcategoryEntity - сущность подкатегории
     * @return - DTO подкатегории
     */
    @Mapping(target = "id", source = "subcategoryEntity.id")
    @Mapping(target = "name", source = "subcategoryEntity.name")
    @Mapping(target = "categoryName", source = "subcategoryEntity.category.name")
    SubcategoryForAdminDto mapToSubcategoryForAdminDto(SubcategoryEntity subcategoryEntity);

    /**
     * Метод позволяет получить сущность подкатегории из DTO. Используется для сохранения подкатегории через админку MVC
     * @param subcategoryForSaveDto - DTO подкатегории
     * @param imagePath - путь к картинке на сервере
     * @param subcategoryImageType - тип картинки
     * @return - сущность подкатегории
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "subcategoryForSaveDto.name")
    @Mapping(target = "subcategoryImage", source = "imagePath")
    @Mapping(target = "subcategoryImageName", source = "subcategoryForSaveDto.subcategoryImage", qualifiedByName = "getNameFromMultipart")
    @Mapping(target = "subcategoryImageType", source = "subcategoryImageType")
    @Mapping(target = "category", source = "category")
    SubcategoryEntity mapToSubcategoryEntity(SubcategoryForSaveAdminDto subcategoryForSaveDto, String imagePath, ImageType subcategoryImageType, CategoryEntity category);

    /**
     * Метод позволяет отредактировать сущность на основе DTO. Используется для редактирования подкатегории через админку MVC
     * @param subcategoryForUpdate - DTO для редактирования
     * @param subcategoryImageType - тип картинки
     * @param imagePath - путь к картинке
     * @param category - категория
     * @param subcategory - сущность подкатегории
     */
    @Mapping(target = "id", source = "subcategoryForUpdate.id")
    @Mapping(target = "name", source = "subcategoryForUpdate.name")
    @Mapping(target = "subcategoryImage", source = "imagePath")
    @Mapping(target = "subcategoryImageName", source = "subcategoryForUpdate.subcategoryImage", qualifiedByName = "getNameFromMultipart")
    @Mapping(target = "subcategoryImageType", source = "subcategoryImageType")
    @Mapping(target = "category", source = "category")
    void mapToSubcategoryEntity(SubcategoryForUpdateAdminDto subcategoryForUpdate, ImageType subcategoryImageType, String imagePath, CategoryEntity category, @MappingTarget SubcategoryEntity subcategory);







    SubcategorySimpleDto mapToSubcategorySimpleDto(SubcategoryEntity subcategoryEntity);

    @Mapping(target = "categoryId", source = "subcategoryEntity.category.id")
    @Mapping(target = "productsIds", source = "products", qualifiedByName = "getProductsIdsFrom")
    @Mapping(target = "subcategoryImageType", source = "subcategoryImageType", qualifiedByName = "getImageType")
    SubcategoryDto mapToSubcategoryDto(SubcategoryEntity subcategoryEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "categoryId", source = "category.id")
    SubcategoryForProductDto mapToSubcategoryForProductDto(SubcategoryEntity subcategoryEntity);

    @Mapping(target = "subcategoryImage", source = "subcategoryForSaveDto.subcategoryImage", qualifiedByName = "multipartFileToBase64")
    @Mapping(target = "subcategoryImageName", source = "subcategoryForSaveDto.subcategoryImage", qualifiedByName = "getNameFromMultipart")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "name", source = "subcategoryForSaveDto.name")
    @Mapping(target = "subcategoryImageType", source = "subcategoryImageType")
    @Mapping(target = "id", ignore = true)
    SubcategoryEntity mapToSubcategoryEntity(SubcategoryForSaveDto subcategoryForSaveDto, ImageType subcategoryImageType, CategoryEntity category);

    @Mapping(target = "subcategoryImage", source = "subcategoryDto.subcategoryImage", qualifiedByName = "multipartFileToBase64")
    @Mapping(target = "subcategoryImageName", source = "subcategoryDto.subcategoryImage", qualifiedByName = "getNameFromMultipart")
    @Mapping(target = "subcategoryImageType", source = "subcategoryImageType")
    void updateSubcategoryFromDto(SubcategoryForUpdateDto subcategoryDto, ImageType subcategoryImageType, @MappingTarget SubcategoryEntity subcategoryEntity);

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

    /**
     * Метод позволит получить формат картинки из типа картинки
     * @param imageType - тип картинки
     * @return - формат картинки
     */
    @Named("getImageType")
    default String getImageType(ImageType imageType){
        return imageType != null ? imageType.getFormat() : null;
    }

    /**
     * Метод позволяет получить имя файла
     * @param file - файл
     * @return - имя
     */
    @Named("getNameFromMultipart")
    default String getNameFromMultipart(MultipartFile file) {
        return file.getOriginalFilename();
    }
}
