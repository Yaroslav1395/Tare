package kg.zavod.Tare.service.product;

import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
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
     * Метод позволяет удалять цвет
     *
     * @param id - id цвет
     */
    void deleteColorById(Integer id);
}
