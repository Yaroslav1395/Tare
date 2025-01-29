package kg.zavod.Tare.mapper.category;

import kg.zavod.Tare.domain.category.CategoryEntity;
import kg.zavod.Tare.dto.category.mvc.CategoryForUserDto;
import kg.zavod.Tare.dto.category.mvc.CategoryForAdminDto;
import kg.zavod.Tare.dto.category.mvc.CategoryForHomeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface CategoryListMapper {

    /**
     * Метод позволяет преобразовать список сущностей категорий в список DTO.
     * Используется в админке MVC
     * @param categories - список сущностей категорий
     * @return - список DTO категорий
     */
    List<CategoryForAdminDto> mapToCategoryForAdminDtoList(List<CategoryEntity> categories);

    /**
     * Метод позволяет преобразовать список сущностей категорий в список DTO.
     * Используется на главной странице MVC
     * @param categories - список сущностей категорий
     * @return - список DTO категорий
     */
    List<CategoryForHomeDto> mapToCategoryForHomeDtoList(List<CategoryEntity> categories);
    List<CategoryForUserDto> mapToCategoryDtoList(List<CategoryEntity> categoryEntityList);
}
