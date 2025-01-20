package kg.zavod.Tare.mapper.notice;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.NoticeEntity;
import kg.zavod.Tare.dto.exception.MultipartFileParseException;
import kg.zavod.Tare.dto.notice.NoticeForAdminDto;
import kg.zavod.Tare.dto.notice.NoticeForSaveAdminDto;
import kg.zavod.Tare.dto.notice.NoticeForUpdateAdminDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Mapper(componentModel = "spring")
public interface NoticeMapper {

    /**
     * Метод позволяет преобразовать сущность новости в DTO
     * @param noticeEntity - сущность новости
     * @return - DTO новости
     */
    @Mapping(target = "imageType", source = "noticeEntity.imageType")
    NoticeForAdminDto mapToNoticeDto(NoticeEntity noticeEntity);

    /**
     * Метод позволяет преобразовать DTO новости в сущность новости для сохранения
     * @param noticeForSaveAdminDto - DTO новости
     * @param imageType - тип картинки
     * @param noticeImagePath - путь к картинке
     * @return - сущность новости
     */
    @Mapping(target = "imageType", source = "imageType")
    @Mapping(target = "noticeImage", source = "noticeImagePath")
    @Mapping(target = "noticeImageName", source = "noticeForSaveAdminDto.noticeImage", qualifiedByName = "getNameFromMultipart")
    NoticeEntity mapToNoticeEntity(NoticeForSaveAdminDto noticeForSaveAdminDto, ImageType imageType, String noticeImagePath);

    /**
     * Метод позволяет отредактировать сущность новости на основе DTO
     * @param noticeForUpdateAdminDto - DTO новости
     * @param notice - сущность новости
     */
    @Mapping(target = "imageType", ignore = true)
    @Mapping(target = "noticeImage", ignore = true)
    @Mapping(target = "noticeImageName", ignore = true)
    void updateNoticeEntity(NoticeForUpdateAdminDto noticeForUpdateAdminDto, @MappingTarget NoticeEntity notice);

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
     * Метод позволяет получить имя файла
     * @param file - файл
     * @return - имя
     */
    @Named("getNameFromMultipart")
    default String getNameFromMultipart(MultipartFile file) {
        return file.getOriginalFilename();
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
