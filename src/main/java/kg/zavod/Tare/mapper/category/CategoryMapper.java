package kg.zavod.Tare.mapper.category;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.category.CategoryEntity;
import kg.zavod.Tare.dto.category.mvc.CategoryForUserDto;
import kg.zavod.Tare.dto.category.CategoryForSaveDto;
import kg.zavod.Tare.dto.category.CategoryForUpdateDto;
import kg.zavod.Tare.dto.category.mvc.*;
import kg.zavod.Tare.dto.exception.MultipartFileParseException;
import kg.zavod.Tare.mapper.subcategory.SubcategoryListMapper;
import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = SubcategoryListMapper.class)
public interface CategoryMapper {
    /**
     * Метод преобразует сущность категории в DTO для клиента
     * @param category - сущность категории
     * @return - DTO категории
     */
    @Mapping(target = "id", source = "category.id")
    @Mapping(target = "name", source = "category.name")
    CategorySimpleForUserDto mapToCategoryForUserDto(CategoryEntity category);

    /**
     * Метод позволяет получить сущность категории из DTO. Используется для редактирования категории через админку MVC
     * @param categoryForUpdateDto - DTO категории для редактирования
     * @param categoryImageType - тип картинки
     * @param imagePath - путь к картинке на сервере
     * @param categoryEntity - сущность категории
     */
    @Mapping(target = "categoryImage", source = "imagePath")
    @Mapping(target = "categoryImageName", source = "categoryForUpdateDto.multipartFile", qualifiedByName = "getNameFromMultipart")
    @Mapping(target = "imageType", source = "categoryImageType")
    void mapToCategoryEntity(CategoryForUpdateAdminDto categoryForUpdateDto, ImageType categoryImageType, String imagePath, @MappingTarget CategoryEntity categoryEntity);

    /**
     * Метод позволяет получить сущность категории из DTO. Используется для сохранения категории через админку MVC
     * @param categoryForSaveDto - DTO категории
     * @param imagePath - путь к картинке на сервере
     * @param categoryImageType - тип картинки
     * @return - сущность категории
     */
    @Mapping(target = "categoryImage", source = "imagePath")
    @Mapping(target = "categoryImageName", source = "categoryForSaveDto.multipartFile", qualifiedByName = "getNameFromMultipart")
    @Mapping(target = "imageType", source = "categoryImageType")
    CategoryEntity mapToCategoryEntity(CategoryForSaveAdminDto categoryForSaveDto, String imagePath, ImageType categoryImageType);

    /**
     * Метод позволяет преобразовать сущность категории в DTO для админки
     * @param categoryEntity - сущность категории
     * @return - DTO категории
     */
    @Mapping(target = "id", source = "categoryEntity.id")
    @Mapping(target = "name", source = "categoryEntity.name")
    @Mapping(target = "imageType", source = "categoryEntity.imageType")
    CategoryForAdminDto mapToCategoryForAdminDto(CategoryEntity categoryEntity);

    /**
     * Метод позволяет преобразовать сущность категории в DTO для главной страницы
     * @param categoryEntity - сущность категории
     * @return - DTO категории
     */
    @Mapping(target = "id", source = "categoryEntity.id")
    @Mapping(target = "name", source = "categoryEntity.name")
    @Mapping(target = "imageType", source = "categoryEntity.imageType")
    CategoryForHomeDto mapToCategoryForHomeDto(CategoryEntity categoryEntity);






    @Mapping(target = "id", source = "id")
    @Mapping(target = "imageType", source = "imageType", qualifiedByName = "getImageType")
    CategoryForUserDto mapToCategoryDto(CategoryEntity categoryEntity);

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
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            return originalFilename.substring(0, originalFilename.indexOf("."));
        }
        return originalFilename;
    }
}
