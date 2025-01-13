package kg.zavod.Tare.service.product.impl;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.product.ColorEntity;
import kg.zavod.Tare.domain.product.ImageEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.image.*;
import kg.zavod.Tare.dto.product.image.mvc.ImageForSaveAdminDto;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
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
        logger.info("Попытка редактирования картинки");
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
     * Метод необходим для редактирования картинок совместно с продуктом
     * @param imagesForUpdate - картинки для редактирования
     * @param product - продукт, который редактируют
     * @return - список отредактированных картинок
     * @throws EntityNotFoundException - в случае если не будет найдено цветов
     */
    @Override
    @Transactional
    public List<ImageDto> updateImages(List<ImageForUpdateWithProductDto> imagesForUpdate, ProductEntity product) throws EntityNotFoundException {
        logger.info("Попытка редактирования картинок");
        List<ImageEntity> imagesFromBase = imageRepository.findAllByProductId(product.getId());
        List<Integer> imageIdForDelete =  filterImagesForDelete(imagesFromBase, imagesForUpdate);
        logger.info("Удаление лишних картинок");
        imageRepository.deleteAllById(imageIdForDelete);
        Set<Integer> colorsId = getColorsIdsForUpdateFrom(imagesForUpdate);
        logger.info("Поиск цветов по id при сохранении картинок");
        Map<Integer, ColorEntity> colors = getColorMapFrom(colorRepository.findAllById(colorsId));
        logger.info("Преобразование картинок в сущности");
        List<ImageEntity> imageForUpdate = imageListMapper.mapToImagesEntityForUpdateWithProduct(
                imagesForUpdate, colors, product, imageMapper);
        logger.info("Сохранение картинок");
        List<ImageEntity> updatedImages = imageRepository.saveAll(imageForUpdate);
        return imageListMapper.mapToImageDtoList(updatedImages);
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
     * @return - список id цветов
     */
    private Set<Integer> getColorsIdsFrom(List<ImageForSaveWithProductDto> images){
        logger.info("Сбор id цветов из списка цветов");
        return images.stream()
                .map(ImageForSaveWithProductDto::getColorId)
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
     * Метод позволяет получить id цветов из списка картинок для сохранения
     * @param images - список картинок для сохранения
     * @return - список id цветов
     */
    private Set<Integer> getColorsIdsForUpdateFrom(List<ImageForUpdateWithProductDto> images){
        logger.info("Сбор id цветов из списка цветов при редактировании");
        return images.stream()
                .map(image -> image.getColor().getId())
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

    /**
     * Метод позволяет вычислить id картинок которые нужно удалить из базы данных.
     * @param imagesFromBase - картинки из базы
     * @param imagesForUpdate - картинки которые нужно отредактировать совместно с продуктом
     * @return - список id
     */
    private List<Integer> filterImagesForDelete(List<ImageEntity> imagesFromBase, List<ImageForUpdateWithProductDto> imagesForUpdate){
        logger.info("Поиск картинок которые нужно удалить");
        Set<Integer> updateIds = imagesForUpdate.stream()
                .map(ImageForUpdateWithProductDto::getId)
                .collect(Collectors.toSet());
        return imagesFromBase.stream()
                .map(ImageEntity::getId)
                .filter(id -> !updateIds.contains(id))
                .collect(Collectors.toList());
    }
}
