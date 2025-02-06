package kg.zavod.Tare.mapper.certificate;

import kg.zavod.Tare.domain.CertificateEntity;
import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.dto.certificate.CertificateForAdminDto;
import kg.zavod.Tare.dto.certificate.CertificateForSaveAdminDto;
import kg.zavod.Tare.dto.certificate.CertificateForUpdateAdminDto;
import kg.zavod.Tare.dto.certificate.CertificateForUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;


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
}
