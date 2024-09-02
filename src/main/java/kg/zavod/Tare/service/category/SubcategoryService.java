package kg.zavod.Tare.service.category;

import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.subcategory.SubcategoryDto;
import kg.zavod.Tare.dto.subcategory.SubcategoryForSaveDto;
import kg.zavod.Tare.dto.subcategory.SubcategoryForUpdateDto;

import java.util.List;

public interface SubcategoryService {
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
     * @throws EntityNotFoundException - в случае если не найдена категория для подкатегории
     * @throws DuplicateEntityException - в случае если в категории уже есть подкатегория с таким названием
     */
    SubcategoryDto saveSubcategory(SubcategoryForSaveDto subcategoryForSaveDto) throws EntityNotFoundException, DuplicateEntityException;

    /**
     * Метод позволят редактировать подкатегорию меняя ее название, картинку и категорию
     * @param subcategoryForUpdateDto - подкатегория для редактирования
     * @return - отредактированная подкатегория
     * @throws EntityNotFoundException - в случае если при редактировании не найдено подкатегории или категории
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
