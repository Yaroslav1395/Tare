package kg.zavod.Tare.service.category;

import kg.zavod.Tare.dto.category.CategoryDto;
import kg.zavod.Tare.dto.category.CategoryForSaveDto;
import kg.zavod.Tare.dto.category.CategoryForUpdateDto;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;

import java.util.List;

public interface CategoryService {
    /**
     * Метод позволяет получить категорию с подкатегориями по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id категории
     * @return - категлоия с подкатегориями
     */
    CategoryDto getCategoryById(Integer id) throws EntityNotFoundException;

    /**
     * Метод позволяет получить все категории с подкатегориями
     * @throws EntitiesNotFoundException - в случае если ни оной категории не найдено
     * @return - список категорий
     */
    List<CategoryDto> getAllCategories() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить категорию
     * @param categoryForSaveDto - категория для сохранения
     * @return - сохраненная категория
     */
    CategoryDto saveCategory(CategoryForSaveDto categoryForSaveDto);

    /**
     * Метод позволят редактировать категорию меняя ее названиее и картинку
     * @param categoryForUpdateDto - категория для редактирования
     * @throws EntityNotFoundException - в случае если при редактировании не найдено
     * @return - отредактированная категория
     */
    CategoryDto updateCategory(CategoryForUpdateDto categoryForUpdateDto) throws EntityNotFoundException;

    /**
     * Метод позволяет удалять категорию
     * @param id - id категории
     * @return - удалена или нет
     */
    Boolean deleteCategoryById(Integer id);
}
