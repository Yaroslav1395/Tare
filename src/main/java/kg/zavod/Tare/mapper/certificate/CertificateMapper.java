package kg.zavod.Tare.mapper.certificate;

import kg.zavod.Tare.domain.CertificateEntity;
import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.dto.certificate.CertificateForAdminDto;
import kg.zavod.Tare.dto.certificate.CertificateForSaveAdminDto;
import kg.zavod.Tare.dto.certificate.CertificateForUpdateAdminDto;
import kg.zavod.Tare.dto.certificate.CertificateForUserDto;
import kg.zavod.Tare.dto.exception.MultipartFileParseException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Mapper(componentModel = "spring")
public interface CertificateMapper {
    /**
     * Метод позволяет преобразовать сущность сертификата в DTO для клиента
     * @param certificateEntity - сущность сертификата
     * @return - DTO сертификата
     */
    CertificateForUserDto mapToCertificateForUserDto(CertificateEntity certificateEntity);

    /**
     * Метод позволяет преобразовать сущность сертификата в DTO
     * @param certificateEntity - сущность сертификата
     * @return - DTO сертификата
     */
    CertificateForAdminDto mapToCertificateDto(CertificateEntity certificateEntity);

    /**
     * Метод позволяет преобразовать DTO сертификата в сущность для сохранения через админку админке
     * @param certificateForSaveAdminDto - сертификат для сохранения
     * @param imageType - тип картинки
     * @param imageTypeKg - тип картинки кыргызской
     * @param imagePath - путь картинки
     * @param imagePathKg - путь картинки кыргызской
     * @return - сущность сертификата
     */
    @Mapping(target = "certificateImageType", source = "imageType")
    @Mapping(target = "certificateImageName", source = "certificateForSaveAdminDto.certificateImage", qualifiedByName = "getNameFromMultipart")
    @Mapping(target = "certificateImage", source = "imagePath")
    @Mapping(target = "certificateImageTypeKg", source = "imageTypeKg")
    @Mapping(target = "certificateImageNameKg", source = "certificateForSaveAdminDto.certificateImageKg", qualifiedByName = "getNameFromMultipart")
    @Mapping(target = "certificateImageKg", source = "imagePathKg")
    CertificateEntity mapToCertificateEntity(CertificateForSaveAdminDto certificateForSaveAdminDto, ImageType imageType, ImageType imageTypeKg, String imagePath, String imagePathKg);

    /**
     * Метод позволяет обновить сущность сертификата
     * @param certificateForUpdateAdminDto - сертификат для обновления
     * @param certificate - сущность сертификата
     */
    @Mapping(target = "certificateImage", ignore = true)
    @Mapping(target = "certificateImageKg", ignore = true)
    void updateCertificateEntity(CertificateForUpdateAdminDto certificateForUpdateAdminDto, @MappingTarget CertificateEntity certificate);

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
