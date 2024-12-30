package kg.zavod.Tare.service.product;

import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.product.*;
import kg.zavod.Tare.dto.product.product.mvc.ProductForHomeDto;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ProductService {

    Map<Integer, List<ProductForHomeDto>> getProductsForHomePage();

    /**
     * Метод позволяет получить продукт по id
     * @param id - id продукта
     * @return - найденный продукт
     * @throws EntityNotFoundException - в случае если продукт не будет найден
     */
    ProductDto getProductById(Integer id) throws EntityNotFoundException;

    /**
     * Метод позволяет получить продукты входящие в подкатегорию по ее id c пагинацией
     * @param subcategoryId - id подкатегории
     * @param pageable - пагинация
     * @return - продукты с пагинацией
     * @throws EntitiesNotFoundException - в случае если ничего не будет найдено
     */
    PageForProduct getProductsBySubcategoryId(Integer subcategoryId, Pageable pageable) throws  EntitiesNotFoundException;
    /**
     * Метод позволяет сохранить продукт
     * @param productForSaveDto - продукт для сохранения
     * @return - сохраненный продукт
     * @throws EntityNotFoundException - в случае если подкатегория, цвет для картинки
     * или характеристики для установки значения не будут найдены
     * @throws EntitiesNotFoundException - в случае если ни одного цвета или характеристики не найдено
     */
    ProductDto saveProduct(ProductForSaveDto productForSaveDto) throws EntityNotFoundException, EntitiesNotFoundException;

    /**
     * Метод позволяет отредактировать продукт
     * @param productForUpdateDto - объект для редактирования
     * @return - отредактированный продукт
     * @throws EntityNotFoundException - в случае если не будет найдено данных для редактирования
     */
    ProductDto updateProduct(ProductForUpdateDto productForUpdateDto) throws EntityNotFoundException;

    /**
     * Метод позволяет удалить продукт
     * @param id - id продукта
     * @return - удален или нет
     */
    Boolean deleteProduct(Integer id);

    /**
     * Метод позволяет получить ссылку на whats app с корзиной клиента
     * @param products - корзина клиента
     * @return - ссылка на whats app
     */
    String getUrlForWhatsAppWithProductBasket(List<ProductFromBasketDro> products);
}
