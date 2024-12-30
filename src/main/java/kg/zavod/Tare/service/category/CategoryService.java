package kg.zavod.Tare.service.category;

import kg.zavod.Tare.dto.category.CategoryDto;
import kg.zavod.Tare.dto.category.CategoryForSaveDto;
import kg.zavod.Tare.dto.category.CategoryForUpdateDto;
import kg.zavod.Tare.dto.category.mvc.CategoryForHomeDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;

import java.util.List;

public interface CategoryService {

    /**
     * Метод позволяет получить все категории для главной страницы MVC
     * @return - список категорий
     * @throws EntitiesNotFoundException - в случае если не найдены категории продуктов
     */
    List<CategoryForHomeDto> getAllCategoriesForHomePage() throws EntitiesNotFoundException;
    /**
     * Метод позволяет получить категорию с подкатегориями по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id категории
     * @return - категория с подкатегориями
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
     * @throws DuplicateEntityException - в случае если дублируется название категории
     * @throws EntityNotFoundException - в случае если формат картинки не будет найден
     */
    CategoryDto saveCategory(CategoryForSaveDto categoryForSaveDto) throws DuplicateEntityException, EntityNotFoundException;

    /**
     * Метод позволят редактировать категорию меняя ее название и картинку
     * @param categoryForUpdateDto - категория для редактирования
     * @return - отредактированная категория
     * @throws EntityNotFoundException - если не будет найдено категории для редактирования или формата картинки
     * @throws DuplicateEntityException - если дублируется название другой категории
     */
    CategoryDto updateCategory(CategoryForUpdateDto categoryForUpdateDto) throws EntityNotFoundException, DuplicateEntityException;

    /**
     * Метод позволяет удалять категорию
     * @param id - id категории
     * @return - удалена или нет
     */
    Boolean deleteCategoryById(Integer id);


}
