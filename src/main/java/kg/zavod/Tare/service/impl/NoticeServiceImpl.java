package kg.zavod.Tare.service.impl;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.NoticeEntity;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.notice.NoticeDto;
import kg.zavod.Tare.dto.notice.NoticeForSaveDto;
import kg.zavod.Tare.dto.notice.NoticeForUpdateDto;
import kg.zavod.Tare.dto.vacancy.VacancyForSaveDto;
import kg.zavod.Tare.dto.vacancy.VacancyForUpdateDto;
import kg.zavod.Tare.mapper.notice.NoticeListMapper;
import kg.zavod.Tare.mapper.notice.NoticeMapper;
import kg.zavod.Tare.repository.NoticeRepository;
import kg.zavod.Tare.service.NoticeService;
import kg.zavod.Tare.service.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final NoticeMapper noticeMapper;
    private final NoticeListMapper noticeListMapper;
    private static final Logger logger = LoggerFactory.getLogger(VacancyServiceImpl.class);

    /**
     * Метод позволяет получить новость по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id новость
     * @return - найденная новость
     */
    @Override
    public NoticeDto getNoticeById(Integer id) throws EntityNotFoundException {
        logger.info("Попытка поиска новости по id");
        NoticeEntity notice = noticeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("По id новости не найдено"));
        return noticeMapper.mapToNoticeDto(notice);
    }

    /**
     * Метод позволяет получить все новости
     * @throws EntitiesNotFoundException - в случае если ни оной новости не найдено
     * @return - список новостей
     */
    @Override
    public List<NoticeDto> getAllNotices() throws EntitiesNotFoundException {
        logger.info("Попытка поиска всех новостей");
        List<NoticeEntity> notices = noticeRepository.findAll();
        if(notices.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одной новости");
        return noticeListMapper.mapToNoticeDtoList(notices);
    }

    /**
     * Метод позволяет получить все активные новости
     * @return - список новостей
     * @throws EntitiesNotFoundException - в случае если ни оной активной новости не найдено
     */
    @Override
    public List<NoticeDto> getAllActiveNotices() throws EntitiesNotFoundException {
        logger.info("Попытка поиска всех активных новостей");
        List<NoticeEntity> notices = noticeRepository.findAllByIsActiveTrue();
        if(notices.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одной новости");
        return noticeListMapper.mapToNoticeDtoList(notices);
    }

    /**
     * Метод позволяет сохранить новость
     * @param noticeForSaveDto - новость для сохранения
     * @throws EntityNotFoundException - если формат картинки не поддерживается приложением
     * @return - сохраненная новость
     */
    @Override
    @Transactional
    public NoticeDto saveNotice(NoticeForSaveDto noticeForSaveDto) throws EntityNotFoundException {
        logger.info("Попытка сохранения новой новости");
        ImageType imageType = UtilService.getImageTypeFrom(noticeForSaveDto.getNoticeImage());
        NoticeEntity noticeEntity = noticeMapper.mapToNoticeEntity(noticeForSaveDto, imageType);
        NoticeEntity savedNotice = noticeRepository.save(noticeEntity);
        return noticeMapper.mapToNoticeDto(savedNotice);
    }

    /**
     * Метод позволят редактировать новость
     * @param noticeForUpdateDto - новость для редактирования
     * @throws EntityNotFoundException - в случае если не найдена новость для редактирования
     * @return - отредактированная новость
     */
    @Override
    @Transactional
    public NoticeDto updateNotice(NoticeForUpdateDto noticeForUpdateDto) throws EntityNotFoundException {
        logger.info("Попытка редактирования новости");
        NoticeEntity noticeEntity = noticeRepository.findById(noticeForUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдено новости по id"));
        ImageType imageType = UtilService.getImageTypeFrom(noticeForUpdateDto.getNoticeImage());
        noticeMapper.updateNoticeEntity(noticeForUpdateDto, imageType, noticeEntity);
        NoticeEntity updatedNotice = noticeRepository.save(noticeEntity);
        return noticeMapper.mapToNoticeDto(updatedNotice);
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
