package kg.zavod.Tare.mapper.partner;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.PartnerEntity;
import kg.zavod.Tare.dto.exception.MultipartFileParseException;
import kg.zavod.Tare.dto.partner.PartnerForAdminDto;
import kg.zavod.Tare.dto.partner.PartnerForSaveAdminDto;
import kg.zavod.Tare.dto.partner.PartnerForUpdateAdminDto;
import kg.zavod.Tare.dto.partner.PartnerForUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Mapper(componentModel = "spring")
public interface PartnerMapper {
    /**
     * Метод позволяет преобразовать сущность партнера в DTO для клиента
     * @param partner - сущность партнера
     * @return - DTO партнера
     */
    PartnerForUserDto mapToPartnerForUserDto(PartnerEntity partner);
    /**
     * Метод позволяет преобразовать сущность партнера в DTO.
     * Используется в админке.
     * @param partnerEntity - сущность партнера
     * @return - DTO партнера
     */
    @Mapping(target = "logoImageType", source = "partnerEntity.logoImageType")
    @Mapping(target = "productImageType", source = "partnerEntity.productImageType")
    PartnerForAdminDto mapToPartnerDto(PartnerEntity partnerEntity);

    /**
     * Метод позволяет преобразовать DTO партнера в сущность при сохранении через админку
     * @param partnerForSaveAdminDto - партнер для сохранения
     * @param logoImageType - тип лого картинки
     * @param logoPath - путь лого картинки
     * @param productImageType - тип картинки продукции
     * @param productPath - путь картинки продукции
     * @return - сущность партнера
     */
    @Mapping(target = "logoImageType", source = "logoImageType")
    @Mapping(target = "productImageType", source = "productImageType")
    @Mapping(target = "logoImage", source = "logoPath")
    @Mapping(target = "productImage", source = "productPath")
    @Mapping(target = "logoImageName", source = "partnerForSaveAdminDto.logoImage", qualifiedByName = "getNameFromMultipart")
    @Mapping(target = "productImageName", source = "partnerForSaveAdminDto.productImage", qualifiedByName = "getNameFromMultipart")
    PartnerEntity mapToPartnerEntity(PartnerForSaveAdminDto partnerForSaveAdminDto, ImageType logoImageType, String logoPath, ImageType productImageType, String productPath);

    /**
     * Метод позволяет отредактировать сущность партнера на основе данных DTO
     * @param partnerForUpdateAdminDto - DTO для сохранения
     * @param partnerEntity - сущность партнера
     */
    @Mapping(target = "logoImage", ignore = true)
    @Mapping(target = "productImage", ignore = true)
    void updatePartnerEntity(PartnerForUpdateAdminDto partnerForUpdateAdminDto, @MappingTarget PartnerEntity partnerEntity);

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
