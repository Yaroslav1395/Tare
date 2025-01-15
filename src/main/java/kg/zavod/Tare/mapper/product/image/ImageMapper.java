package kg.zavod.Tare.mapper.product.image;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.product.ColorEntity;
import kg.zavod.Tare.domain.product.ImageEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.exception.MultipartFileParseException;
import kg.zavod.Tare.dto.product.image.*;
import kg.zavod.Tare.dto.product.image.mvc.ImageForAdminDto;
import kg.zavod.Tare.dto.product.image.mvc.ImageForProductHomeDto;
import kg.zavod.Tare.dto.product.image.mvc.ImageForSaveAdminDto;
import kg.zavod.Tare.dto.product.image.mvc.ImageForUpdateAdminDto;
import kg.zavod.Tare.mapper.product.color.ColorMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Mapper(componentModel = "spring", uses = ColorMapper.class)
public interface ImageMapper {

    /**
     * Метод позволяет преобразовать DTO картинки продукта в сущность картинки продукта.
     * @param imageForSaveDto - DTO картинки продукта
     * @param colors - список цветов
     * @param product - сущность продукта
     * @param productImageType - тип картинки
     * @return - сущность картинки продукта
     * @throws EntityNotFoundException - в случае если подходящие цветы не будут найдены по id
     */
    @Mapping(target = "productImage", source = "imageForSaveDto.imagePath")
    @Mapping(target = "productImageName", source = "imageForSaveDto.productImage", qualifiedByName = "getNameFromMultipart")
    @Mapping(target = "color", expression = "java(getColorFromMvc(imageForSaveDto, colors))")
    @Mapping(target = "imageType", source = "productImageType")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "id", ignore = true)
    ImageEntity mapToImageEntityMvc(ImageForSaveAdminDto imageForSaveDto, Map<Integer, ColorEntity> colors, ProductEntity product, ImageType productImageType) throws EntityNotFoundException;

    /**
     * Метод позволяет преобразовать DTO картинки продукта в сущность картинки продукта при редактировании.
     * @param imageForUpdateDto - DTO картинки продукта
     * @param colors - список цветов
     * @param product - сущность продукта
     * @param productImageType - тип картинки
     * @return - сущность картинки продукта
     * @throws EntityNotFoundException - в случае если подходящие цветы не будут найдены по id
     */
    @Mapping(target = "productImage", source = "imageForUpdateDto.imagePath")
    @Mapping(target = "productImageName", source = "imageForUpdateDto.productImage", qualifiedByName = "getNameFromMultipart")
    @Mapping(target = "color", expression = "java(getColorForUpdateFrom(imageForUpdateDto, colors))")
    @Mapping(target = "imageType", source = "productImageType")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "id", expression = "java(getIdMvcFrom(imageForUpdateDto))")
    ImageEntity mapToImageEntityUpdateMvc(ImageForUpdateAdminDto imageForUpdateDto, Map<Integer, ColorEntity> colors, ProductEntity product, ImageType productImageType) throws EntityNotFoundException;



    /**
     * Метод позволяет преобразовать сущность картинки продукта в DTO для админки MVC
     * @param imageEntity - сущность картинки продукта
     * @return - DTO картинки продукта
     */
    @Mapping(target = "id", source = "imageEntity.id")
    @Mapping(target = "colorId", source = "imageEntity.color.id")
    ImageForAdminDto mapToImageForAdminDto(ImageEntity imageEntity);


    /**
     * Метод преобразовывает сущность картинки продукта в DTO для главной страницы MVC
     * @param imageEntity - сущность картинки продукта
     * @return - DTO картинки продукта
     */
    @Mapping(target = "productImageType", source = "imageType", qualifiedByName = "getImageType")
    ImageForProductHomeDto mapToImageForProductHomeDto(ImageEntity imageEntity);


    /**
     * Метод необходим для получения id картинки при редактировании совместно с продуктом. Если вызывать преобразование
     * напрямую, то в случае null будет 500
     * @param image - картинка для редактирования
     * @return - id картинки
     */
    default Integer getIdMvcFrom(ImageForUpdateAdminDto image){
        return image.getId();
    }








    @Mapping(target = "productId", source = "image.product.id")
    @Mapping(target = "productImageType", source = "image.imageType", qualifiedByName = "getImageType")
    ImageDto mapToImageDto(ImageEntity image);

    @Mapping(target = "productImage", source = "imageForSaveDto.productImage", qualifiedByName = "multipartFileToBase64")
    @Mapping(target = "productImageName", source = "imageForSaveDto.productImage", qualifiedByName = "getNameFromMultipart")
    @Mapping(target = "color", source = "color")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "imageType", source = "productImageType")
    @Mapping(target = "id", ignore = true)
    ImageEntity mapToImageEntity(ImageForSaveDto imageForSaveDto, ColorEntity color, ProductEntity product, ImageType productImageType);

    @Mapping(target = "productImage", source = "imageForUpdateDto.productImage", qualifiedByName = "multipartFileToBase64")
    @Mapping(target = "productImageName", source = "imageForUpdateDto.productImage", qualifiedByName = "getNameFromMultipart")
    @Mapping(target = "color", expression = "java(getColorFromForUpdate(imageForUpdateDto, colors))")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "imageType", source = "productImageType")
    @Mapping(target = "id", expression = "java(getIdFrom(imageForUpdateDto))")
    ImageEntity mapToUpdateWithProductImageEntity(ImageForUpdateWithProductDto imageForUpdateDto, Map<Integer, ColorEntity> colors, ProductEntity product, ImageType productImageType) throws EntityNotFoundException;

    @Mapping(target = "productImage", source = "imageForSaveDto.productImage", qualifiedByName = "multipartFileToBase64")
    @Mapping(target = "productImageName", source = "imageForSaveDto.productImage", qualifiedByName = "getNameFromMultipart")
    @Mapping(target = "color", expression = "java(getColorFrom(imageForSaveDto, colors))")
    @Mapping(target = "imageType", source = "productImageType")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "id", ignore = true)
    ImageEntity mapToImageEntity(ImageForSaveWithProductDto imageForSaveDto, Map<Integer, ColorEntity> colors, ProductEntity product, ImageType productImageType) throws EntityNotFoundException;

    @Mapping(target = "productImage", source = "imageForUpdateDto.productImage", qualifiedByName = "multipartFileToBase64")
    @Mapping(target = "productImageName", source = "imageForUpdateDto.productImage", qualifiedByName = "getNameFromMultipart")
    @Mapping(target = "color", source = "color")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "imageType", source = "productImageType")
    @Mapping(target = "id", ignore = true)
    void updateImage(ImageForUpdateDto imageForUpdateDto, ProductEntity product, ColorEntity color, ImageType productImageType, @MappingTarget ImageEntity image);


    /**
     * Метод позволит получить формат картинки из типа картинки
     * @param imageType - тип картинки
     * @return - формат картинки
     */
    @Named("getImageType")
    default String getImageType(ImageType imageType){
        return imageType.getFormat();
    }

    /**
     * Метод позволяет преобразовать MultipartFile в строку Base64
     * @param file - картинка как MultipartFile
     * @return - картинка как Base64
     */
    @Named("multipartFileToBase64")
    default String multipartFileToBase64(MultipartFile file) {
        try {
            byte[] fileContent = file.getBytes();
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new MultipartFileParseException("Ошибка при преобразовании MultipartFile в Base64");
        }
    }

    /**
     * Метод позволяет получить имя файла
     * @param file - файл
     * @return - имя
     */
    @Named("getNameFromMultipart")
    default String getNameFromMultipart(MultipartFile file) {
        return file.getOriginalFilename();
    }

    /**
     * Метод позволяет найти нужный цвет для картинки из словаря цветов
     * @param imageForSaveDto - картинка для сохранения
     * @param colors - словарь цветов
     * @return - найденный цвет
     * @throws EntityNotFoundException - в случае если цвет не найден
     */
    default ColorEntity getColorFrom(ImageForSaveWithProductDto imageForSaveDto, Map<Integer, ColorEntity> colors) throws EntityNotFoundException {
        ColorEntity color = colors.get(imageForSaveDto.getColorId());
        if(color == null) throw new EntityNotFoundException("По id " + imageForSaveDto.getColorId() + "не найдено цвета");
        return color;
    }

    /**
     * Метод позволяет найти нужный цвет для картинки из словаря цветов
     * @param imageForSaveDto - картинка для сохранения
     * @param colors - словарь цветов
     * @return - найденный цвет
     * @throws EntityNotFoundException - в случае если цвет не найден
     */
    default ColorEntity getColorFromMvc(ImageForSaveAdminDto imageForSaveDto, Map<Integer, ColorEntity> colors) throws EntityNotFoundException {
        ColorEntity color = colors.get(imageForSaveDto.getColorId());
        if(color == null) throw new EntityNotFoundException("По id " + imageForSaveDto.getColorId() + "не найдено цвета");
        return color;
    }

    /**
     * Метод позволяет найти нужный цвет для картинки из словаря цветов при редактировании
     * @param imageForSaveDto - картинка для сохранения
     * @param colors - словарь цветов
     * @return - найденный цвет
     * @throws EntityNotFoundException - в случае если цвет не найден
     */
    default ColorEntity getColorForUpdateFrom(ImageForUpdateAdminDto imageForSaveDto, Map<Integer, ColorEntity> colors) throws EntityNotFoundException {
        ColorEntity color = colors.get(imageForSaveDto.getColorId());
        if(color == null) throw new EntityNotFoundException("По id " + imageForSaveDto.getColorId() + "не найдено цвета");
        return color;
    }

    /**
     * Метод позволяет найти нужный цвет для картинки из словаря цветов
     * @param imageForUpdateDto - картинка для сохранения
     * @param colors - словарь цветов
     * @return - найденный цвет
     * @throws EntityNotFoundException - в случае если цвет не найден
     */
    default ColorEntity getColorFromForUpdate(ImageForUpdateWithProductDto imageForUpdateDto, Map<Integer, ColorEntity> colors) throws EntityNotFoundException {
        ColorEntity color = colors.get(imageForUpdateDto.getColor().getId());
        if(color == null) throw new EntityNotFoundException("По id " + imageForUpdateDto.getColor().getId() + "не найдено цвета");
        return color;
    }

    /**
     * Метод необходим для получения id картинки при редактировании совместно с продуктом. Если вызывать преобразование
     * напрямую, то в случае null будет 500
     * @param image - картинка для редактирования
     * @return - id картинки
     */
    default Integer getIdFrom(ImageForUpdateWithProductDto image){
        return image.getId();
    }
}
