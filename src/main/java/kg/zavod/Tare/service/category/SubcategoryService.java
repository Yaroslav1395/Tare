package kg.zavod.Tare.service.category;

import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForAdminDto;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForSaveAdminDto;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForUpdateAdminDto;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForUserDto;

import java.io.IOException;
import java.util.List;

public interface SubcategoryService {

    /**
     * Метод позволяет получить подкатегорию для клиента MVC.
     * @param subcategoryId - id подкатегории
     * @return - подкатегория
     * @throws EntityNotFoundException - в случае если подкатегория не будет найдена
     */
    SubcategoryForUserDto getSubcategoryForUserById(Integer subcategoryId) throws EntityNotFoundException;
    /**
     * Метод позволяет получить подкатегории для страницы подкатегории по id категории. Используется в MVC
     * @param categoryId - id категории
     * @return - список подкатегорий
     * @throws EntitiesNotFoundException - в случае если ничего не найдено
     */
    List<SubcategoryForUserDto> getSubcategoryForUserByCategoryId(Integer categoryId) throws EntitiesNotFoundException;

    /**
     * Метод позволяет получить подкатегории для админки. Используется в MVC
     * @return - список подкатегорий
     * @throws EntitiesNotFoundException - в случае если ничего не найдено
     */
    List<SubcategoryForAdminDto> getSubcategoriesForAdmin() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить подкатегорию. Для админки MVC
     * @param subcategoryForSaveDto - данные подкатегории
     * @throws DuplicateEntityException - в случае если подкатегория уже существует
     * @throws IOException - в случае если не удалось сохранить картинку
     * @throws EntityNotFoundException - в случае если формат картинки не поддерживается
     */
    void saveSubcategory(SubcategoryForSaveAdminDto subcategoryForSaveDto) throws DuplicateEntityException, IOException, EntityNotFoundException;

    /**
     * Метод позволяет отредактировать подкатегорию. Для админки MVC
     * @param subcategoryForUpdate - данные подкатегории для редактирования
     * @throws DuplicateEntityException - в случае если подкатегория уже существует
     * @throws IOException - в случае если не удалось сохранить картинку
     * @throws EntityNotFoundException - в случае если формат не картинки поддерживается
     */
    void updateSubcategory(SubcategoryForUpdateAdminDto subcategoryForUpdate) throws EntityNotFoundException, IOException,DuplicateEntityException;

    /**
     * Метод позволяет удалять подкатегорию
     *
     * @param id - id подкатегории
     */
    void deleteSubcategoryById(Integer id);
}
