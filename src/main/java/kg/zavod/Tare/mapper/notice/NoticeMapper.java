package kg.zavod.Tare.mapper.notice;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.NoticeEntity;
import kg.zavod.Tare.dto.notice.NoticeForAdminDto;
import kg.zavod.Tare.dto.notice.NoticeForSaveAdminDto;
import kg.zavod.Tare.dto.notice.NoticeForUpdateAdminDto;
import kg.zavod.Tare.dto.notice.NoticeForUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;


@Mapper(componentModel = "spring")
public interface NoticeMapper {
    /**
     * Метод позволят преобразовать сущность новости в DTO для клиента
     * @param notice - сущность новости
     * @return - DTO новости
     */
    NoticeForUserDto mapToNoticeForUserDto(NoticeEntity notice);

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
     * Метод позволяет получить имя файла
     * @param file - файл
     * @return - имя
     */
    @Named("getNameFromMultipart")
    default String getNameFromMultipart(MultipartFile file) {
        return file.getOriginalFilename();
    }
}
