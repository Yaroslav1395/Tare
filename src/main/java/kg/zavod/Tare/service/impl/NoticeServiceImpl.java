package kg.zavod.Tare.service.impl;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.NoticeEntity;
import kg.zavod.Tare.domain.PartnerEntity;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.notice.NoticeForAdminDto;
import kg.zavod.Tare.dto.notice.NoticeForSaveAdminDto;
import kg.zavod.Tare.dto.notice.NoticeForUpdateAdminDto;
import kg.zavod.Tare.dto.notice.NoticeForUserDto;
import kg.zavod.Tare.dto.partner.PartnerForUpdateAdminDto;
import kg.zavod.Tare.mapper.notice.NoticeListMapper;
import kg.zavod.Tare.mapper.notice.NoticeMapper;
import kg.zavod.Tare.repository.NoticeRepository;
import kg.zavod.Tare.service.NoticeService;
import kg.zavod.Tare.service.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final NoticeMapper noticeMapper;
    private final NoticeListMapper noticeListMapper;
    @Value("${file.save.basic.path}")
    private String basicPath;
    @Value("${base.url.load}")
    private String baseUrlForLoad;
    private static final Logger logger = LoggerFactory.getLogger(VacancyServiceImpl.class);

    /**
     * Метод позволяет получить все новости для клиента
     * @return - список новостей
     */
    @Override
    public List<NoticeForUserDto> getAllNoticesForUser() {
        logger.info("Попытка поиска всех новостей для клиента");
        List<NoticeEntity> notices = noticeRepository.findAll();
        List<NoticeForUserDto> noticesDto = noticeListMapper.mapToNoticeForUserDtoList(notices);
        noticesDto.forEach(notice -> notice.setNoticeImage(baseUrlForLoad + notice.getNoticeImage()));
        noticesDto.sort(Comparator.comparing(NoticeForUserDto::getCreatedTime).reversed());
        return noticesDto;
    }

    /**
     * Метод позволяет получить все новости
     * @return - список новостей
     */
    @Override
    public List<NoticeForAdminDto> getAllNoticesForAdmin() {
        logger.info("Попытка поиска всех новостей");
        List<NoticeEntity> notices = noticeRepository.findAll();
        List<NoticeForAdminDto> noticesDto = noticeListMapper.mapToNoticeAdminDtoList(notices);
        noticesDto.forEach(notice -> notice.setNoticeImage(baseUrlForLoad + notice.getNoticeImage()));
        return noticesDto;
    }

    /**
     * Метод позволяет сохранить новость
     * @param noticeForSaveAdminDto - новость для сохранения
     * @throws EntityNotFoundException - если формат картинки не поддерживается приложением
     * @throws IOException - в случае если невозможно сохранить картинку
     */
    @Override
    @Transactional
    public void saveNotice(NoticeForSaveAdminDto noticeForSaveAdminDto) throws EntityNotFoundException, IOException {
        logger.info("Попытка сохранения новой новости");
        ImageType imageType = UtilService.getImageTypeFrom(noticeForSaveAdminDto.getNoticeImage());
        String path = UtilService.saveImage(noticeForSaveAdminDto.getNoticeImage(), "notices", basicPath);
        NoticeEntity noticeEntity = noticeMapper.mapToNoticeEntity(noticeForSaveAdminDto, imageType, path);
        noticeRepository.save(noticeEntity);
    }

    /**
     * Метод позволят редактировать новость
     * @param noticeForUpdateAdminDto - новость для редактирования
     * @throws EntityNotFoundException - в случае если не найдена новость для редактирования
     * @throws IOException - в случае если невозможно сохранить картинку
     */
    @Override
    @Transactional
    public void updateNotice(NoticeForUpdateAdminDto noticeForUpdateAdminDto) throws EntityNotFoundException, IOException {
        logger.info("Попытка редактирования новости");
        NoticeEntity noticeEntity = noticeRepository.findById(noticeForUpdateAdminDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдено новости по id"));
        noticeMapper.updateNoticeEntity(noticeForUpdateAdminDto, noticeEntity);
        updateNoticeImage(noticeEntity, noticeForUpdateAdminDto);
        noticeRepository.save(noticeEntity);
    }

    /**
     * Метод позволяет удалять новость
     * @param id - id новости
     * @return - удалена или нет
     */
    @Override
    public boolean deleteNoticeById(Integer id) {
        logger.info("Попытка удаления новости");
        noticeRepository.deleteById(id);
        return !noticeRepository.existsById(id);
    }

    /**
     * Метод позволяет обновить картинку новости
     * @param noticeEntity - сущность новости
     * @param noticeForUpdateAdminDto - объект для редактирования
     * @throws EntityNotFoundException - в случае если тип не поддерживается
     * @throws IOException - в случае если не удалось сохранить картинку
     */
    private void updateNoticeImage(NoticeEntity noticeEntity, NoticeForUpdateAdminDto noticeForUpdateAdminDto) throws EntityNotFoundException, IOException {
        logger.info("Обновление логотипа партнера");
        if(noticeForUpdateAdminDto.getNoticeImage().getSize() > 1) {
            ImageType imageType = UtilService.getImageTypeFrom(noticeForUpdateAdminDto.getNoticeImage());
            String imagePath = UtilService.saveImage(noticeForUpdateAdminDto.getNoticeImage(), "notices", basicPath);
            String fileName = noticeForUpdateAdminDto.getNoticeImage().getOriginalFilename();
            noticeEntity.setNoticeImage(imagePath);
            noticeEntity.setImageType(imageType);
            noticeEntity.setNoticeImageName(fileName);
        }
    }







    /**
     * Метод позволяет получить новость по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id новость
     * @return - найденная новость
     */
    @Override
    public NoticeForAdminDto getNoticeById(Integer id) throws EntityNotFoundException {
        logger.info("Попытка поиска новости по id");
        NoticeEntity notice = noticeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("По id новости не найдено"));
        return noticeMapper.mapToNoticeDto(notice);
    }

    /**
     * Метод позволяет получить все активные новости
     * @return - список новостей
     * @throws EntitiesNotFoundException - в случае если ни оной активной новости не найдено
     */
    @Override
    public List<NoticeForAdminDto> getAllActiveNotices() throws EntitiesNotFoundException {
        logger.info("Попытка поиска всех активных новостей");
        List<NoticeEntity> notices = noticeRepository.findAllByIsActiveTrue();
        if(notices.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одной новости");
        return noticeListMapper.mapToNoticeAdminDtoList(notices);
    }

    /**
     * Метод позволяет изменять активность новости
     * @param id - id новости активность которой нужно изменить
     * @param isActive - флаг активности
     * @return - текущее состояние новости
     */
    @Override
    @Transactional
    public boolean changeNoticeActivityById(Integer id, Boolean isActive) {
        logger.info("Попытка изменения активности новости");
        int active = noticeRepository.updateIsActiveById(id, isActive);
        return active == 1;
    }
}
