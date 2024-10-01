package kg.zavod.Tare.mapper.category;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.category.CategoryEntity;
import kg.zavod.Tare.dto.category.CategoryDto;
import kg.zavod.Tare.dto.category.CategoryForSaveDto;
import kg.zavod.Tare.dto.category.CategoryForUpdateDto;
import kg.zavod.Tare.dto.exception.MultipartFileParseException;
import kg.zavod.Tare.mapper.subcategory.SubcategoryListMapper;
import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = SubcategoryListMapper.class)
public interface CategoryMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "imageType", source = "imageType", qualifiedByName = "getImageType")
    CategoryDto mapToCategoryDto(CategoryEntity categoryEntity);

    @Mapping(target = "categoryImage", source = "categoryForUpdateDto.multipartFile", qualifiedByName = "multipartFileToBase64")
    @Mapping(target = "categoryImageName", source = "categoryForUpdateDto.multipartFile", qualifiedByName = "getNameFromMultipart")
    @Mapping(target = "imageType", source = "categoryImageType")
    CategoryEntity mapToCategoryEntity(CategoryForUpdateDto categoryForUpdateDto,  ImageType categoryImageType, @MappingTarget CategoryEntity categoryEntity);

    @Mapping(target = "categoryImage", source = "categoryForSaveDto.multipartFile", qualifiedByName = "multipartFileToBase64")
    @Mapping(target = "categoryImageName", source = "categoryForSaveDto.multipartFile", qualifiedByName = "getNameFromMultipart")
    @Mapping(target = "imageType", source = "categoryImageType")
    CategoryEntity mapToCategoryEntity(CategoryForSaveDto categoryForSaveDto, ImageType categoryImageType);

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
        return imageType.getFormat();
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
