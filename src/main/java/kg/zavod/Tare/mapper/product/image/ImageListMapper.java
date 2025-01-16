package kg.zavod.Tare.mapper.product.image;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.product.ColorEntity;
import kg.zavod.Tare.domain.product.ImageEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.image.*;
import kg.zavod.Tare.dto.product.image.mvc.ImageForAdminDto;
import kg.zavod.Tare.dto.product.image.mvc.ImageForSaveAdminDto;
import kg.zavod.Tare.dto.product.image.mvc.ImageForUpdateAdminDto;
import kg.zavod.Tare.dto.product.image.mvc.ImageForUserDto;
import kg.zavod.Tare.service.util.UtilService;
import org.mapstruct.*;

import java.util.*;

@Mapper(componentModel = "spring", uses = ImageMapper.class)
public interface ImageListMapper {

    /**
     * Метод позволяет преобразовать список сущностей в список DTO картинок продукта. Используется в клиенте MVC
     * @param images - список сущностей картинок
     * @return - список DTO картинок
     */
    List<ImageForUserDto> mapToImageForUserDtoList(List<ImageEntity> images);

    /**
     * Метод позволяет преобразовать список сущностей в список DTO картинок продукта. Используется в админке MVC
     * @param images - список сущностей
     * @return - список DTO картинок
     */
    List<ImageForAdminDto> mapToImageForAdminDtoList(List<ImageEntity> images);

    /**
     * Метод преобразовывает список DTO картинок продукта в список сущностей. Используется в админке MVC
     * @param images - список картинок продукта
     * @param colors - словарь цветов
     * @param product - сущность продукта
     * @param imageMapper - маппер картинок
     * @return - список сущностей
     * @throws EntityNotFoundException - в случае если не найдено подходящего цвета по id
     */
    default ArrayList<ImageEntity> mapToImageEntityListMvc(List<ImageForSaveAdminDto> images, Map<Integer, ColorEntity> colors, ProductEntity product, ImageMapper imageMapper) throws EntityNotFoundException {
        if (images == null) {
            return new ArrayList<>();
        }
        ArrayList<ImageEntity> imageEntities = new ArrayList<>();
        for (ImageForSaveAdminDto image : images) {
            ImageType productImageType = UtilService.getImageTypeFrom(image.getProductImage());
            imageEntities.add(imageMapper.mapToImageEntityMvc(image, colors, product, productImageType));
        }
        return imageEntities;
    }

    /**
     * Метод список DTO картинок продукта в список сущностей. Используется в админке MVC
     * @param images - список картинок продукта
     * @param colors - словарь цветов
     * @param product - сущность продукта
     * @param imageMapper - маппер картинок
     * @return - список сущностей
     * @throws EntityNotFoundException - в случае если не найдено подходящего цвета по id
     */
    default ArrayList<ImageEntity> mapToImageEntityListUpdateMvc(List<ImageForUpdateAdminDto> images, Map<Integer, ColorEntity> colors, ProductEntity product, ImageMapper imageMapper) throws EntityNotFoundException {
        if (images == null) {
            return new ArrayList<>();
        }
        ArrayList<ImageEntity> imageEntities = new ArrayList<>();
        for (ImageForUpdateAdminDto image : images) {
            ImageType productImageType = UtilService.getImageTypeFrom(image.getProductImage());
            imageEntities.add(imageMapper.mapToImageEntityUpdateMvc(image, colors, product, productImageType));
        }
        return imageEntities;
    }










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
     * Метод преобразовывает картинки для сохранения в базу данных при редактировании продукта. Пользователь может
     * добавить к продукту новую картинку. Либо отредактировать старую
     * @param imagesForUpdate - картинки для редактирования
     * @param colors - словарь цветов для поиска необходимого
     * @param product - продукт, который редактируют
     * @param imageMapper - преобразователь картинки
     * @return - список преобразованных картинок в сущности
     * @throws EntityNotFoundException - в случае если не найдется необходимого цвета
     */
    default List<ImageEntity> mapToImagesEntityForUpdateWithProduct(List<ImageForUpdateWithProductDto> imagesForUpdate, Map<Integer, ColorEntity> colors, ProductEntity product, ImageMapper imageMapper) throws EntityNotFoundException {
        if(imagesForUpdate == null) {
            return new ArrayList<>();
        }
        ArrayList<ImageEntity> imageEntities = new ArrayList<>();
        for(ImageForUpdateWithProductDto image : imagesForUpdate){
            ImageType productImageType = UtilService.getImageTypeFrom(image.getProductImage());
            imageEntities.add(imageMapper.mapToUpdateWithProductImageEntity(image, colors, product, productImageType));
        }
        return imageEntities;
    }
}
