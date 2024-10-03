package kg.zavod.Tare.mapper.certificate;

import kg.zavod.Tare.domain.CertificateEntity;
import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.dto.certificate.CertificateDto;
import kg.zavod.Tare.dto.certificate.CertificateForSaveDto;
import kg.zavod.Tare.dto.certificate.CertificateForUpdateDto;
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
    @Mapping(target = "certificateImageType", source = "certificateEntity.certificateImageType", qualifiedByName = "getImageType")
    CertificateDto mapToCertificateDto(CertificateEntity certificateEntity);

    @Mapping(target = "certificateImageType", source = "imageType")
    @Mapping(target = "certificateImageName", source = "certificateForSaveDto.certificateImage", qualifiedByName = "getNameFromMultipart")
    @Mapping(target = "certificateImage", source = "certificateForSaveDto.certificateImage", qualifiedByName = "multipartFileToBase64")
    CertificateEntity mapToCertificateEntity(CertificateForSaveDto certificateForSaveDto, ImageType imageType);

    @Mapping(target = "certificateImageType", source = "imageType")
    @Mapping(target = "certificateImageName", source = "certificateForUpdateDto.certificateImage", qualifiedByName = "getNameFromMultipart")
    @Mapping(target = "certificateImage", source = "certificateForUpdateDto.certificateImage", qualifiedByName = "multipartFileToBase64")
    void updateCertificateEntity(CertificateForUpdateDto certificateForUpdateDto, ImageType imageType, @MappingTarget CertificateEntity certificate);

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
}
