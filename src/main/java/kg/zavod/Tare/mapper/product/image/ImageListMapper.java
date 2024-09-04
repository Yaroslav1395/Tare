package kg.zavod.Tare.mapper.product.image;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.product.ColorEntity;
import kg.zavod.Tare.domain.product.ImageEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.image.ImageDto;
import kg.zavod.Tare.dto.product.image.ImageForSaveDto;
import kg.zavod.Tare.dto.product.image.ImageForSaveWithProductDto;
import kg.zavod.Tare.service.util.UtilService;
import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
}
