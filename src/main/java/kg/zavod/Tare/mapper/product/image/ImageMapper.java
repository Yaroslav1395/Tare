package kg.zavod.Tare.mapper.product.image;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.product.ColorEntity;
import kg.zavod.Tare.domain.product.ImageEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.exception.MultipartFileParseException;
import kg.zavod.Tare.dto.product.image.ImageDto;
import kg.zavod.Tare.dto.product.image.ImageForSaveDto;
import kg.zavod.Tare.dto.product.image.ImageForSaveWithProductDto;
import kg.zavod.Tare.dto.product.image.ImageForUpdateDto;
import kg.zavod.Tare.mapper.product.color.ColorMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring", uses = ColorMapper.class)
public interface ImageMapper {
    @Mapping(target = "productId", source = "image.product.id")
    @Mapping(target = "productImageType", source = "image.imageType", qualifiedByName = "getImageType")
    ImageDto mapToImageDto(ImageEntity image);

    @Mapping(target = "productImage", source = "imageForSaveDto.productImage", qualifiedByName = "multipartFileToBase64")
    @Mapping(target = "color", source = "color")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "imageType", source = "imageForSaveDto.productImage", qualifiedByName = "extractImageType")
    @Mapping(target = "id", ignore = true)
    ImageEntity mapToImageEntity(ImageForSaveDto imageForSaveDto, ColorEntity color, ProductEntity product);

    @Mapping(target = "productImage", source = "imageForSaveDto.productImage", qualifiedByName = "multipartFileToBase64")
    @Mapping(target = "color", expression = "java(getColorFrom(imageForSaveDto, colors))")
    @Mapping(target = "imageType", source = "imageForSaveDto.productImage", qualifiedByName = "extractImageType")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "id", ignore = true)
    ImageEntity mapToImageEntity(ImageForSaveWithProductDto imageForSaveDto, Map<Integer, ColorEntity> colors, ProductEntity product) throws EntityNotFoundException;

    @Mapping(target = "productImage", source = "imageForUpdateDto.productImage", qualifiedByName = "multipartFileToBase64")
    @Mapping(target = "color", source = "color")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "id", ignore = true)
    void updateImage(ImageForUpdateDto imageForUpdateDto, ProductEntity product, ColorEntity color, @MappingTarget ImageEntity image);

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
     * Метод позволяет вычислить формат картинки
     * @param image - картинка
     * @return - формат
     */
    @Named("extractImageType")
    default ImageType extractImageType(MultipartFile image) {
        String contentType = image.getContentType();
        if (contentType != null && contentType.startsWith("image/")) {
            return ImageType.fromContentType(contentType)
                    .orElseThrow(() -> new MultipartFileParseException("Формат файла не поддерживается приложением"));
        }
        throw new MultipartFileParseException("Файл не является картинкой");
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
}
