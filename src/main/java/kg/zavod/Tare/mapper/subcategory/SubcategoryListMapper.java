package kg.zavod.Tare.mapper.subcategory;

import kg.zavod.Tare.domain.category.SubcategoryEntity;
import kg.zavod.Tare.dto.subcategory.SubcategoryDto;
import kg.zavod.Tare.dto.subcategory.SubcategorySimpleDto;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForAdminDto;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForHomeDto;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForUserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SubcategoryMapper.class, SubcategoryMapperForMvc.class})
public interface SubcategoryListMapper {
    /**
     * Метод позволяет преобразовать список сущностей подкатегорий в список DTO. Используется на странице подкатегорий MVC
     * @param subcategories - список сущностей подкатегорий
     * @return - список DTO подкатегорий
     */
    List<SubcategoryForUserDto> mapToSubcategoryForUserDtoList(List<SubcategoryEntity> subcategories);

    /**
     * Метод позволяет преобразовать список сущностей подкатегорий в список DTO для админки MVC.
     * @param subcategories - список сущностей подкатегорий
     * @return - список DTO подкатегорий
     */
    List<SubcategoryForAdminDto> mapToSubcategoryForAdminDtoList(List<SubcategoryEntity> subcategories);

    /**
     * Метод позволяет преобразовать список сущностей подкатегорий в список DTO.
     * Используется на главной странице MVC.
     * @param subcategories - список сущностей подкатегорий
     * @return - список DTO подкатегорий
     */
    List<SubcategoryForHomeDto> mapToSubcategoryForHomeDtoList(List<SubcategoryEntity> subcategories);


    List<SubcategorySimpleDto> mapToSubcategorySimpleDto(List<SubcategoryEntity> subcategoryEntityList);
    List<SubcategoryDto> mapToSubcategoryDtoList(List<SubcategoryEntity> subcategories);
}
