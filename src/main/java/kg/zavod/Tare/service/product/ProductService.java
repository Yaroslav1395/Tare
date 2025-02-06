package kg.zavod.Tare.service.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.product.mvc.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ProductService {
    /**
     * Метод позволяет найти продукты для корзины. Принимает строку JSON и преобразует в список объектов
     * @param products - список продуктов как JSON
     * @return - список продуктов для корзины
     */
    List<ProductForBasketDto> getProductsForBasket(String products) throws JsonProcessingException;
    /**
     * Метод позволяет найти продукт по id для клиента MVC
     * @param productId - id продукта
     * @return - продукт
     * @throws EntityNotFoundException - в случае если продукт не найден
     */
    ProductForUserDto getProductForUserById(Integer productId) throws EntityNotFoundException;

    /**
     * Метод позволяет получить продукты для клиента по id подкатегории. Используется в клиенте MVC
     * @param subcategoryId - id подкатегории
     * @return - список продуктов
     * @throws EntitiesNotFoundException - в случае если продукты не найдены
     */
    List<ProductForUserDto> getProductsForUserBySubcategoryId(Integer subcategoryId) throws EntitiesNotFoundException;

    /**
     * Метод позволяет найти продукты по поисковому запросу. Используется клиентом
     * @param search - поисковая строка
     * @return - список продуктов
     * @throws EntitiesNotFoundException - в случае если продукты не найдены
     */
    List<ProductForUserDto> getProductsBySearch(String search) throws EntitiesNotFoundException;

    /**
     * Метод позволяет получить все продукты. Используется в админке MVC
     * @return - список продуктов
     * @throws EntitiesNotFoundException - в случае если продукты не найдены
     */
    List<ProductForAdminDto> getProductsForAdmin() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить продукт. Используется в админке MVC
     * @param productForSaveAdminDto - продукт для сохранения
     * @throws EntityNotFoundException - в случае если подкатегория, цвет для картинки или характеристика не будут найдены
     */
    void saveProductAdminMvc(ProductForSaveAdminDto productForSaveAdminDto) throws EntityNotFoundException, EntitiesNotFoundException, IOException;

    /**
     * Метод позволяет отредактировать продукт. Используется в админке MVC
     * @param productForUpdateAdminDto - продукт для редактирования
     * @throws EntityNotFoundException - в случае если продукт не будет найден
     * @throws EntitiesNotFoundException - в случае если подкатегория, цвет для картинки или характеристика не будут найдены
     * @throws IOException - в случае если не получится сохранить картинку
     */
    void updateProductAdminMvc(ProductForUpdateAdminDto productForUpdateAdminDto) throws EntityNotFoundException, EntitiesNotFoundException, IOException;

    /**
     * Метод позволяет получить продукты категории бутылки и банки для главной страницы. Берется по
     * 2 продукта из каждой подкатегории.
     * @return - словарь продуктов по категориям
     */
    Map<Integer, List<ProductForUserDto>> getProductsForHomePage();

    /**
     * Метод позволяет удалить продукт
     *
     * @param id - id продукта
     */
    void deleteProduct(Integer id);
}
