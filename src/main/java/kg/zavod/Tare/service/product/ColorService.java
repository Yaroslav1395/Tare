package kg.zavod.Tare.service.product;

import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.color.ColorDto;
import kg.zavod.Tare.dto.product.color.ColorForSaveDto;
import kg.zavod.Tare.dto.product.color.ColorForUpdateDto;
import kg.zavod.Tare.dto.product.color.mvc.ColorForAdminDto;
import kg.zavod.Tare.dto.product.color.mvc.ColorForSaveAdminDto;
import kg.zavod.Tare.dto.product.color.mvc.ColorForUpdateAdminDto;

import java.util.List;

public interface ColorService {
    /**
     * Метод позволяет получить цвета картинок продукта для админки MVC
     * @return - список цветов
     * @throws EntitiesNotFoundException - в случае если цвета не найдены
     */
    List<ColorForAdminDto> getAllColorsForAdmin() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить новый цвет. Используется в админке MVC
     * @param colorForSaveDto - новый цвет
     * @throws DuplicateEntityException - в случае если такой цвет уже существует
     * @throws EntityNotFoundException - если цвет для редактирования не найден
     */
    void saveColor(ColorForSaveAdminDto colorForSaveDto) throws DuplicateEntityException, EntityNotFoundException;

    /**
     * Метод позволяет отредактировать цвет. Используется в админке MVC
     * @param colorForUpdateAdmin - цвет для редактирования
     * @throws DuplicateEntityException - в случае если такой цвет уже существует
     */
    void updateColor(ColorForUpdateAdminDto colorForUpdateAdmin) throws DuplicateEntityException, EntityNotFoundException;




    /**
     * Метод позволяет получить цвет по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id цвета
     * @return - цвет
     */
    ColorDto getColorById(Integer id) throws EntityNotFoundException;

    /**
     * Метод позволяет получить все цвета
     * @throws EntitiesNotFoundException - в случае если ни оного цвета не найдено
     * @return - список цветов
     */
    List<ColorDto> getAllColors() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить цвет
     * @param colorForSaveDto - цвет для сохранения
     * @throws DuplicateEntityException - в случае если название цвета или hex код цвета уже существует
     * @return - сохраненный цвет
     */
    ColorDto saveColor(ColorForSaveDto colorForSaveDto) throws DuplicateEntityException;

    /**
     * Метод позволят редактировать цвет
     * @param colorForUpdateDto - цвет для редактирования
     * @throws EntityNotFoundException - в случае если при редактировании не найдено цвета
     * @throws DuplicateEntityException - в случае если название цвета или hex код цвета уже существует
     * @return - отредактированный цвет
     */
    ColorDto updateColor(ColorForUpdateDto colorForUpdateDto) throws EntityNotFoundException, DuplicateEntityException;

    /**
     * Метод позволяет удалять цвет
     * @param id - id цвет
     * @return - удален или нет
     */
    Boolean deleteColorById(Integer id);
}
