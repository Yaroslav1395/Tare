package kg.zavod.Tare.service.category;

import kg.zavod.Tare.dto.category.mvc.CategoryForUpdateAdminDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.subcategory.SubcategoryDto;
import kg.zavod.Tare.dto.subcategory.SubcategoryForSaveDto;
import kg.zavod.Tare.dto.subcategory.SubcategoryForUpdateDto;
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
     * Ме6тод позволяет получить подкатегорию по id продукта. Используется в MVC
     * @param productId - id продукта
     * @return - подкатегория
     * @throws EntityNotFoundException - в случае если подкатегория не будет найдена
     */
    SubcategoryForUserDto getSubcategoryByProductId(Integer productId) throws EntityNotFoundException;

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
     * Метод позволяет получить подкатегорию со списком id продуктов по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id подкатегории
     * @return - подкатегория со списком id продуктов
     */
    SubcategoryDto getSubcategoryById(Integer id) throws EntityNotFoundException;

    /**
     * Метод позволяет получить все подкатегории со списком id продуктов
     * @throws EntitiesNotFoundException - в случае если ни оной подкатегории не найдено
     * @return - список подкатегорий
     */
    List<SubcategoryDto> getAllSubcategories() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить подкатегорию
     * @param subcategoryForSaveDto - подкатегория для сохранения
     * @return - сохраненная подкатегория
     * @throws EntityNotFoundException - в случае если не найдена категория для подкатегории или формат картинки
     * @throws DuplicateEntityException - в случае если в категории уже есть подкатегория с таким названием
     */
    SubcategoryDto saveSubcategory(SubcategoryForSaveDto subcategoryForSaveDto) throws EntityNotFoundException, DuplicateEntityException;

    /**
     * Метод позволят редактировать подкатегорию меняя ее название, картинку и категорию
     * @param subcategoryForUpdateDto - подкатегория для редактирования
     * @return - отредактированная подкатегория
     * @throws EntityNotFoundException - в случае если при редактировании не найдено подкатегории или категории или формат картинки
     * @throws DuplicateEntityException - в случае если в категории дублируется подкатегория
     */
    SubcategoryDto updateSubcategory(SubcategoryForUpdateDto subcategoryForUpdateDto) throws EntityNotFoundException, DuplicateEntityException;

    /**
     * Метод позволяет удалять подкатегорию
     * @param id - id подкатегории
     * @return - удалена или нет
     */
    boolean deleteSubcategoryById(Integer id);
}
