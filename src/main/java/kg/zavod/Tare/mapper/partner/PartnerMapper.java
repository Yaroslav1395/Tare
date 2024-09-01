package kg.zavod.Tare.mapper.partner;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.PartnerEntity;
import kg.zavod.Tare.dto.exception.MultipartFileParseException;
import kg.zavod.Tare.dto.partner.PartnerDto;
import kg.zavod.Tare.dto.partner.PartnerForSaveDto;
import kg.zavod.Tare.dto.partner.PartnerForUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Mapper(componentModel = "spring")
public interface PartnerMapper {
    @Mapping(target = "logoImageType", source = "partnerEntity.logoImageType", qualifiedByName = "getImageType")
    @Mapping(target = "productImageType", source = "partnerEntity.productImageType", qualifiedByName = "getImageType")
    PartnerDto mapToPartnerDto(PartnerEntity partnerEntity);

    @Mapping(target = "logoImageType", source = "logoImageType")
    @Mapping(target = "productImageType", source = "productImageType")
    @Mapping(target = "logoImage", source = "partnerForSaveDto.logoImage", qualifiedByName = "multipartFileToBase64")
    @Mapping(target = "productImage", source = "partnerForSaveDto.productImage", qualifiedByName = "multipartFileToBase64")
    PartnerEntity mapToPartnerEntity(PartnerForSaveDto partnerForSaveDto, ImageType logoImageType, ImageType productImageType);

    @Mapping(target = "logoImageType", source = "logoImageType")
    @Mapping(target = "productImageType", source = "productImageType")
    @Mapping(target = "logoImage", source = "partnerForUpdateDto.logoImage", qualifiedByName = "multipartFileToBase64")
    @Mapping(target = "productImage", source = "partnerForUpdateDto.productImage", qualifiedByName = "multipartFileToBase64")
    void updatePartnerEntity(PartnerForUpdateDto partnerForUpdateDto, ImageType logoImageType, ImageType productImageType, @MappingTarget PartnerEntity partnerEntity);

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
}
