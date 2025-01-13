package kg.zavod.Tare.service.product;

import kg.zavod.Tare.domain.product.ImageEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.image.*;
import kg.zavod.Tare.dto.product.image.mvc.ImageForSaveAdminDto;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    /**
     * Метод позволяет сохранить список картинок продукта. Используется в админке MVC
     * @param imagesDto - список картинок
     * @param product - продукт, которому принадлежат картинки
     * @throws EntityNotFoundException - в случае если цвет или продукт не будут найдены
     * @throws EntitiesNotFoundException - в случае если не найдено подходящего цвета по id
     * @throws IOException - при ошибке сохранения
     */
     void saveImagesAdminMvc(List<ImageForSaveAdminDto> imagesDto, ProductEntity product) throws EntityNotFoundException, EntitiesNotFoundException, IOException;





    /**
     * Метод позволяет получить картинку продукта по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id картинки
     * @return - картинка продукта
     */
    ImageDto getImageById(Integer id) throws EntityNotFoundException;

    /**
     * Метод позволяет получить все картинки продуктов
     * @throws EntitiesNotFoundException - в случае если ни одной картинки не найдено
     * @return - список картинок продуктов
     */
    List<ImageDto> getAllImages() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить картинку продукта
     * @param imageForSaveDto - картинка продукта для сохранения
     * @throws EntityNotFoundException - в случае если при сохранении не найдено цвета или продукта
     * @return - сохраненная картинка продукта
     */
    ImageDto saveImage(ImageForSaveDto imageForSaveDto) throws EntityNotFoundException;

    /**
     * Метод позволяет сохранить картинки продукта
     * @param imagesForSaveDto - список картинок для сохранения
     * @param product - продукт для которого необходимо сохранить картинки
     * @return - сохраненные картинки
     * @throws EntitiesNotFoundException - в случае если цвета для картинки не будут найдены
     */
    List<ImageDto> saveImages(List<ImageForSaveWithProductDto> imagesForSaveDto, ProductEntity product) throws EntitiesNotFoundException, EntityNotFoundException;

    /**
     * Метод позволят редактировать картинку продукта
     * @param imageForUpdateDto - картинка продукта для редактирования
     * @throws EntityNotFoundException - в случае если при редактировании не найдено картинки, цвета или продукта
     * @return - отредактированная картинка продукта
     */
    ImageDto updateImage(ImageForUpdateDto imageForUpdateDto) throws EntityNotFoundException;

    /**
     * Метод необходим для редактирования картинок совместно с продуктом
     * @param imagesForUpdate - картинки для редактирования
     * @param product - продукт, который редактируют
     * @return - список отредактированных картинок
     * @throws EntityNotFoundException - в случае если не будет найдено цветов
     */
    List<ImageDto> updateImages(List<ImageForUpdateWithProductDto> imagesForUpdate, ProductEntity product) throws EntityNotFoundException;

    /**
     * Метод позволяет удалять картинку продукта
     * @param id - id картинки продукта
     * @return - удалена или нет
     */
    Boolean deleteImageById(Integer id);
}
