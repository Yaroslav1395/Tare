package kg.zavod.Tare.service.product;

import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.image.ImageForSaveAdminDto;
import kg.zavod.Tare.dto.product.image.ImageForUpdateAdminDto;

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
     * Метод позволяет отредактировать список картинок продукта. Используется в админке MVC
     * @param imagesDto - список картинок
     * @param product - продукт, которому принадлежат картинки
     * @throws EntityNotFoundException - в случае если не найдено подходящего цвета по id
     * @throws EntitiesNotFoundException - в если картинки не будут найдены
     * @throws IOException - при ошибке сохранения
     */
     void updateImagesAdminMvc(List<ImageForUpdateAdminDto> imagesDto, ProductEntity product) throws EntityNotFoundException, EntitiesNotFoundException, IOException;
}
