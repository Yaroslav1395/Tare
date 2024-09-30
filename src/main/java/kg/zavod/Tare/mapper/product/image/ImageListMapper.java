package kg.zavod.Tare.mapper.product.image;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.product.ColorEntity;
import kg.zavod.Tare.domain.product.ImageEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.image.*;
import kg.zavod.Tare.service.util.UtilService;
import org.mapstruct.*;

import java.util.*;

@Mapper(componentModel = "spring", uses = ImageMapper.class)
public interface ImageListMapper {
    List<ImageDto> mapToImageDtoList(List<ImageEntity> images);

    default ArrayList<ImageEntity> mapToImageEntityList(List<ImageForSaveWithProductDto> images, Map<Integer, ColorEntity> colors, ProductEntity product, ImageMapper imageMapper) throws EntityNotFoundException {
        if (images == null) {
            return new ArrayList<>();
        }
        ArrayList<ImageEntity> imageEntities = new ArrayList<>();
        for (ImageForSaveWithProductDto image : images) {
            ImageType productImageType = UtilService.getImageTypeFrom(image.getProductImage());
            imageEntities.add(imageMapper.mapToImageEntity(image, colors, product, productImageType));
        }
        return imageEntities;
    }

    /**
     * Метод преобразовывает картинки которые уже есть в базе данных при редактировании продукта. Пользователь может
     * изменить картинку или поменять цвет. Данные изменения должны отобразиться в базе
     * @param imagesForUpdate - картинки для редактирования с id
     * @param colors - словарь цветов для поиска необходимого
     * @param product - продукт, который редактируют
     * @param imageMapper - преобразователь картинки
     * @return - список преобразованных картинок в сущности
     * @throws EntityNotFoundException - в случае если не найдется необходимого цвета
     */
    default HashSet<ImageEntity> mapToImagesEntityForUpdateWithProduct(List<ImageForUpdateWithProductDto> imagesForUpdate, Map<Integer, ColorEntity> colors, ProductEntity product, ImageMapper imageMapper) throws EntityNotFoundException {
        if(imagesForUpdate == null) {
            return new HashSet<>();
        }
        HashSet<ImageEntity> imageEntities = new HashSet<>();
        for(ImageForUpdateWithProductDto image : imagesForUpdate){
            ImageType productImageType = UtilService.getImageTypeFrom(image.getProductImage());
            imageEntities.add(imageMapper.mapToImageEntity(image, colors, product, productImageType));
        }
        return imageEntities;
    }

    /**
     * Метод преобразовывает картинки для сохранения в базу данных при редактировании продукта. Пользователь может
     * добавить к продукту новую картинку. Либо отредактировать старую
     * @param imagesForUpdate - картинки для редактирования
     * @param colors - словарь цветов для поиска необходимого
     * @param product - продукт, который редактируют
     * @param imageMapper - преобразователь картинки
     * @return - список преобразованных картинок в сущности
     * @throws EntityNotFoundException - в случае если не найдется необходимого цвета
     */
    default List<ImageEntity> mapToImagesEntityForSaveWithProduct(List<ImageForUpdateWithProductDto> imagesForUpdate, Map<Integer, ColorEntity> colors, ProductEntity product, ImageMapper imageMapper) throws EntityNotFoundException {
        if(imagesForUpdate == null) {
            return new ArrayList<>();
        }
        ArrayList<ImageEntity> imageEntities = new ArrayList<>();
        for(ImageForUpdateWithProductDto image : imagesForUpdate){
            ImageType productImageType = UtilService.getImageTypeFrom(image.getProductImage());
            imageEntities.add(imageMapper.mapToNewImageEntity(image, colors, product, productImageType));
        }
        return imageEntities;
    }
}
