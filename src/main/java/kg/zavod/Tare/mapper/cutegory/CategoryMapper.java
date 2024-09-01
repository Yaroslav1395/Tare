package kg.zavod.Tare.mapper.cutegory;

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

    CategoryDto mapToCategoryDto(CategoryEntity categoryEntity);

    @Mapping(target = "categoryImage", source = "multipartFile", qualifiedByName = "multipartFileToBase64")
    CategoryEntity mapToCategoryEntity(CategoryForUpdateDto categoryForUpdateDto, @MappingTarget CategoryEntity categoryEntity);

    @Mapping(target = "categoryImage", source = "multipartFile", qualifiedByName = "multipartFileToBase64")
    CategoryEntity mapToCategoryEntity(CategoryForSaveDto categoryForSaveDto);

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
