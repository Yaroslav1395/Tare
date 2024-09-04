package kg.zavod.Tare.service.product.impl;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.product.ColorEntity;
import kg.zavod.Tare.domain.product.ImageEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.image.ImageDto;
import kg.zavod.Tare.dto.product.image.ImageForSaveDto;
import kg.zavod.Tare.dto.product.image.ImageForSaveWithProductDto;
import kg.zavod.Tare.dto.product.image.ImageForUpdateDto;
import kg.zavod.Tare.mapper.product.image.ImageListMapper;
import kg.zavod.Tare.mapper.product.image.ImageMapper;
import kg.zavod.Tare.repository.product.ColorRepository;
import kg.zavod.Tare.repository.product.ImageRepository;
import kg.zavod.Tare.repository.product.ProductRepository;
import kg.zavod.Tare.service.product.ImageService;
import kg.zavod.Tare.service.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;
    private final ImageMapper imageMapper;
    private final ImageListMapper imageListMapper;
    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    /**
     * Метод позволяет получить картинку продукта по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id картинки
     * @return - картинка продукта
     */
    @Override
    @Transactional(readOnly = true)
    public ImageDto getImageById(Integer id) throws EntityNotFoundException {
        logger.info("Попытка поиска картинки по id");
        ImageEntity imageEntity = imageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("По Id не найдено картинки"));
        return imageMapper.mapToImageDto(imageEntity);
    }

    /**
     * Метод позволяет получить все картинки продуктов
     * @throws EntitiesNotFoundException - в случае если ни одной картинки не найдено
     * @return - список картинок продуктов
     */
    @Override
    @Transactional(readOnly = true)
    public List<ImageDto> getAllImages() throws EntitiesNotFoundException {
        logger.info("Попытка получить все картинки");
        List<ImageEntity> images = imageRepository.findAll();
        if(images.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одной картинки");
        return imageListMapper.mapToImageDtoList(images);
    }

    /**
     * Метод позволяет сохранить картинку продукта
     * @param imageForSaveDto - картинка продукта для сохранения
     * @throws EntityNotFoundException - в случае если при сохранении не найдено цвета или продукта
     * @return - сохраненная картинка продукта
     */
    @Override
    @Transactional
    public ImageDto saveImage(ImageForSaveDto imageForSaveDto) throws EntityNotFoundException {
        logger.info("Попытка сохранения картинки");
        logger.info("Поиск цвета по id при сохранении картинки");
        ColorEntity colorEntity = colorRepository.findById(imageForSaveDto.getColorId())
                .orElseThrow(() -> new EntityNotFoundException("По Id не найдено цвета"));
        logger.info("Поиск продукта по id при сохранении картинки");
        ProductEntity productEntity = productRepository.findById(imageForSaveDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("По Id не найдено продукта"));
        ImageType productImageType = UtilService.getImageTypeFrom(imageForSaveDto.getProductImage());
        ImageEntity imageForSave = imageMapper.mapToImageEntity(imageForSaveDto, colorEntity, productEntity, productImageType);
        ImageEntity savedImage = imageRepository.save(imageForSave);
        return imageMapper.mapToImageDto(savedImage);
    }

    /**
     * Метод позволяет сохранить картинки продукта
     * @param imagesForSaveDto - список картинок для сохранения
     * @param product - продукт для которого необходимо сохранить картинки
     * @return - сохраненные картинки
     * @throws EntitiesNotFoundException - в случае если цвета для картинки не будут найдены
     * @throws EntityNotFoundException - в случае если не будет найден цвет для картинки
     */
    @Override
    @Transactional
    public List<ImageDto> saveImages(List<ImageForSaveWithProductDto> imagesForSaveDto, ProductEntity product) throws EntitiesNotFoundException, EntityNotFoundException {
        logger.info("Попытка сохранения картинок для продукта");
        Set<Integer> colorsId = getColorsIdsFrom(imagesForSaveDto);
        logger.info("Поиск цветов по id при сохранении картинок");
        Map<Integer, ColorEntity> colors = getColorMapFrom(colorRepository.findAllById(colorsId));
        if(colors.isEmpty()) throw new EntitiesNotFoundException("Не найдено цветов по id");
        logger.info("Преобразование списка картинок в сущности для сохранения");
        List<ImageEntity> imagesForSave = imageListMapper.mapToImageEntityList(imagesForSaveDto, colors, product, imageMapper);
        logger.info("Сохранение картинок после преобразования");
        List<ImageEntity> savedImages = imageRepository.saveAll(imagesForSave);
        return imageListMapper.mapToImageDtoList(savedImages);
    }

    /**
     * Метод позволят редактировать картинку продукта
     * @param imageForUpdateDto - картинка продукта для редактирования
     * @throws EntityNotFoundException - в случае если при редактировании не найдено картинки, цвета или продукта
     * @return - отредактированная картинка продукта
     */
    @Override
    @Transactional
    public ImageDto updateImage(ImageForUpdateDto imageForUpdateDto) throws EntityNotFoundException {
        logger.info("Попытка редактирования цвета");
        logger.info("Поиск картинки по id");
        ImageEntity imageEntity = imageRepository.findById(imageForUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("По Id не найдено картинки"));
        logger.info("Поиск цвета по id при редактировании картинки");
        ColorEntity colorEntity = colorRepository.findById(imageForUpdateDto.getColorId())
                .orElseThrow(() -> new EntityNotFoundException("По Id не найдено цвета"));
        logger.info("Поиск продукта по id при редактировании картинки");
        ProductEntity productEntity = productRepository.findById(imageForUpdateDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("По Id не найдено продукта"));
        ImageType productImageType = UtilService.getImageTypeFrom(imageForUpdateDto.getProductImage());
        imageMapper.updateImage(imageForUpdateDto, productEntity, colorEntity, productImageType, imageEntity);
        ImageEntity savedImage = imageRepository.save(imageEntity);
        return imageMapper.mapToImageDto(savedImage);
    }

    /**
     * Метод позволяет удалять картинку продукта
     * @param id - id картинки продукта
     * @return - удалена или нет
     */
    @Override
    @Transactional
    public Boolean deleteImageById(Integer id) {
        logger.info("Попытка удаления картинки");
        imageRepository.deleteById(id);
        return !imageRepository.existsById(id);
    }

    /**
     * Метод позволяет получить id цветов из списка картинок для сохранения
     * @param images - список картинок для сохранения
     * @return - списк id цветов
     */
    private Set<Integer> getColorsIdsFrom(List<ImageForSaveWithProductDto> images){
        logger.info("Сбор id цветов из списка цветов");
        return images.stream()
                .map(ImageForSaveWithProductDto::getColorId)
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
