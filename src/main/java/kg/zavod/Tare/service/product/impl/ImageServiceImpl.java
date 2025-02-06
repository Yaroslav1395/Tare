package kg.zavod.Tare.service.product.impl;


import kg.zavod.Tare.domain.product.ColorEntity;
import kg.zavod.Tare.domain.product.ImageEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.image.mvc.ImageForSaveAdminDto;
import kg.zavod.Tare.dto.product.image.mvc.ImageForUpdateAdminDto;
import kg.zavod.Tare.mapper.product.image.ImageListMapper;
import kg.zavod.Tare.mapper.product.image.ImageMapper;
import kg.zavod.Tare.repository.product.ColorRepository;
import kg.zavod.Tare.repository.product.ImageRepository;
import kg.zavod.Tare.service.product.ImageService;
import kg.zavod.Tare.service.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ColorRepository colorRepository;
    private final ImageMapper imageMapper;
    private final ImageListMapper imageListMapper;
    @Value("${file.save.basic.path}")
    private String basicPath;
    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    /**
     * Метод позволяет сохранить список картинок продукта. Используется в админке MVC
     * @param imagesDto - список картинок
     * @param product - продукт, которому принадлежат картинки
     * @throws EntityNotFoundException - в случае если цвет или продукт не будут найдены
     * @throws EntitiesNotFoundException - в случае если не найдено подходящего цвета по id
     * @throws IOException - при ошибке сохранения
     */
    @Override
    public void saveImagesAdminMvc(List<ImageForSaveAdminDto> imagesDto, ProductEntity product) throws EntityNotFoundException, EntitiesNotFoundException, IOException {
        logger.info("Попытка сохранения картинки через админку MVC");
        imagesDto.removeIf(dto -> dto.getColorId() == null || dto.getProductImage() == null);
        Set<Integer> colorsId = getColorsMvcIdsFrom(imagesDto);
        logger.info("Поиск цветов по id при сохранении картинок mvc");
        Map<Integer, ColorEntity> colors = getColorMapFrom(colorRepository.findAllById(colorsId));
        if(colors.isEmpty()) throw new EntitiesNotFoundException("Не найдено цветов по id");
        logger.info("Преобразование списка картинок в сущности для сохранения. MVC");
        for(ImageForSaveAdminDto image : imagesDto) {
            image.setImagePath(UtilService.saveImage(image.getProductImage(), "products", basicPath));
        }
        List<ImageEntity> imagesForSave = imageListMapper.mapToImageEntityListMvc(imagesDto, colors, product, imageMapper);
        logger.info("Сохранение картинок после преобразования mvc");
        imageRepository.saveAll(imagesForSave);
    }

    /**
     * Метод позволяет отредактировать список картинок продукта. Используется в админке MVC
     * @param imagesForUpdate - список картинок
     * @param product - продукт, которому принадлежат картинки
     * @throws EntityNotFoundException - в случае если не найдено подходящего цвета по id
     * @throws EntitiesNotFoundException - в если картинки не будут найдены
     * @throws IOException - при ошибке сохранения
     */
    @Override
    //TODO: отредактировать
    public void updateImagesAdminMvc(List<ImageForUpdateAdminDto> imagesForUpdate, ProductEntity product) throws EntityNotFoundException, EntitiesNotFoundException, IOException {
        logger.info("Попытка редактирования картинок mvc");
        Set<Integer> updatedImagesId = imagesForUpdate.stream()
                .map(ImageForUpdateAdminDto::getId)
                .collect(Collectors.toSet());
        List<ImageEntity> imagesFromBase = imageRepository.findAllByProductId(product.getId());
        logger.info("Поиск цветов по id при редактировании картинок mvc");
        Set<Integer> colorsId = getColorsIdsForUpdateMvcFrom(imagesForUpdate);
        Map<Integer, ColorEntity> colors = getColorMapFrom(colorRepository.findAllById(colorsId));
        logger.info("удаление ненужных картинок");
        List<Integer> imageIdsForDelete =  imagesFromBase.stream()
                .map(ImageEntity::getId)
                .filter(id -> !updatedImagesId.contains(id))
                .toList();
        imageRepository.deleteAllByIdIn(imageIdsForDelete);
        List<ImageForUpdateAdminDto> filteredImagesForUpdate = imagesForUpdate.stream()
                .filter(dto -> dto.getColorId() != null && dto.getProductImage().getSize() > 0)
                .toList();
        for(ImageForUpdateAdminDto image : filteredImagesForUpdate) {
            image.setImagePath(UtilService.saveImage(image.getProductImage(), "products", basicPath));
        }
        List<ImageEntity>  newImagesEntityForSave = imageListMapper.mapToImageEntityListUpdateMvc(filteredImagesForUpdate, colors, product, imageMapper);
        imageRepository.saveAll(newImagesEntityForSave);

        List<ImageForUpdateAdminDto> imageForColorUpdate = imagesForUpdate.stream()
                .filter(dto -> dto.getId() != null && dto.getColorId() != null && dto.getProductImage().getSize() < 1)
                .toList();
        List<ImageEntity> imageEntitiesForColorUpdate = imageRepository.findAllById(imageForColorUpdate.stream().map(ImageForUpdateAdminDto::getId).toList());
        for(ImageForUpdateAdminDto image : imageForColorUpdate) {
            for (ImageEntity imageEntity : imageEntitiesForColorUpdate) {
                if(image.getId().equals(imageEntity.getId())) {
                    imageEntity.setColor(colors.get(image.getColorId()));
                }
            }
        }
        imageRepository.saveAll(imageEntitiesForColorUpdate);
    }

    /**
     * Метод позволяет получить id цветов из списка картинок для сохранения
     * @param images - список картинок для сохранения
     * @return - список id цветов
     */
    private Set<Integer> getColorsIdsForUpdateMvcFrom(List<ImageForUpdateAdminDto> images){
        logger.info("Сбор id цветов из списка цветов при редактировании");
        return images.stream()
                .map(ImageForUpdateAdminDto::getColorId)
                .collect(Collectors.toSet());
    }

    /**
     * Метод позволяет получить id цветов из списка картинок для сохранения
     * @param images - список картинок для сохранения
     * @return - список id цветов
     */
    private Set<Integer> getColorsMvcIdsFrom(List<ImageForSaveAdminDto> images){
        logger.info("Сбор id цветов из списка цветов. MVC");
        return images.stream()
                .map(ImageForSaveAdminDto::getColorId)
                .collect(Collectors.toSet());
    }

    /**
     * Метод позволяет преобразовать список цветов в словарь
     * @param colors - список цветов
     * @return - словарь цветов
     */
    private Map<Integer, ColorEntity> getColorMapFrom(List<ColorEntity> colors){
        logger.info("Преобразование списка цветов в словарь");
        return colors.stream()
                .collect(Collectors.toMap(ColorEntity::getId, color -> color));
    }
}
