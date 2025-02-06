package kg.zavod.Tare.service.category;

import kg.zavod.Tare.dto.category.*;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    /**
     * Метод позволяет получить все категории с подкатегориями
     * @throws EntitiesNotFoundException - в случае если ни оной категории не найдено
     * @return - список категорий
     */
    List<CategoryForUserDto> getAllCategories() throws EntitiesNotFoundException;

    /**
     * Метод позволяет получить категорию для клиента по id
     * @param categoryId - id категории
     * @return - категория
     * @throws EntityNotFoundException - в случае если категория не будет найдена
     */
    CategorySimpleForUserDto getCategoryForUserById(Integer categoryId) throws EntityNotFoundException;

    /**
     * Метод позволяет получить все категории для админки MVC
     * @return - список категорий
     * @throws EntitiesNotFoundException - в случае если ничего не найдено
     */
    List<CategoryForAdminDto> getAllCategoriesForAdmin() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить категорию. Для админки MVC
     * @param categoryForSaveDto - данные категории
     * @throws DuplicateEntityException - в случае если категория уже существует
     * @throws IOException - в случае если не удалось сохранить картинку
     * @throws EntityNotFoundException - в случае если формат не картинки поддерживается
     */
    void saveCategory(CategoryForSaveAdminDto categoryForSaveDto) throws DuplicateEntityException, IOException, EntityNotFoundException;

    /**
     * Метод позволяет отредактировать категорию. Для админки MVC
     * @param categoryForUpdate - данные категории для редактирования
     * @throws DuplicateEntityException - в случае если категория уже существует
     * @throws IOException - в случае если не удалось сохранить картинку
     * @throws EntityNotFoundException - в случае если формат не картинки поддерживается
     */
    void updateCategory(CategoryForUpdateAdminDto categoryForUpdate) throws EntityNotFoundException, IOException,DuplicateEntityException;

    /**
     * Метод позволяет удалять категорию
     *
     * @param id - id категории
     */
    void deleteCategoryById(Integer id);
}
