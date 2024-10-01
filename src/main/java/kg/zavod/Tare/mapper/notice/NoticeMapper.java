package kg.zavod.Tare.mapper.notice;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.NoticeEntity;
import kg.zavod.Tare.dto.exception.MultipartFileParseException;
import kg.zavod.Tare.dto.notice.NoticeDto;
import kg.zavod.Tare.dto.notice.NoticeForSaveDto;
import kg.zavod.Tare.dto.notice.NoticeForUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Mapper(componentModel = "spring")
public interface NoticeMapper {
    @Mapping(target = "imageType", source = "noticeEntity.imageType", qualifiedByName = "getImageType")
    NoticeDto mapToNoticeDto(NoticeEntity noticeEntity);

    @Mapping(target = "imageType", source = "imageType")
    @Mapping(target = "noticeImage", source = "noticeForSaveDto.noticeImage", qualifiedByName = "multipartFileToBase64")
    @Mapping(target = "noticeImageName", source = "noticeForSaveDto.noticeImage", qualifiedByName = "getNameFromMultipart")
    NoticeEntity mapToNoticeEntity(NoticeForSaveDto noticeForSaveDto, ImageType imageType);

    @Mapping(target = "imageType", source = "imageType")
    @Mapping(target = "noticeImage", source = "noticeForUpdateDto.noticeImage", qualifiedByName = "multipartFileToBase64")
    @Mapping(target = "noticeImageName", source = "noticeForUpdateDto.noticeImage", qualifiedByName = "getNameFromMultipart")
    void updateNoticeEntity(NoticeForUpdateDto noticeForUpdateDto, ImageType imageType, @MappingTarget NoticeEntity notice);

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
