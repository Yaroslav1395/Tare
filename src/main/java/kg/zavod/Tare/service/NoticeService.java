package kg.zavod.Tare.service;

import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.notice.NoticeDto;
import kg.zavod.Tare.dto.notice.NoticeForSaveDto;
import kg.zavod.Tare.dto.notice.NoticeForUpdateDto;

import java.util.List;

public interface NoticeService {
    /**
     * Метод позволяет получить новость по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id новость
     * @return - найденная новость
     */
    NoticeDto getNoticeById(Integer id) throws EntityNotFoundException;

    /**
     * Метод позволяет получить все новости
     * @throws EntitiesNotFoundException - в случае если ни оной новости не найдено
     * @return - список новостей
     */
    List<NoticeDto> getAllNotices() throws EntitiesNotFoundException;

    /**
     * Метод позволяет получить все активные новости
     * @return - список новостей
     * @throws EntitiesNotFoundException - в случае если ни оной активной новости не найдено
     */
    List<NoticeDto> getAllActiveNotices() throws EntitiesNotFoundException;

    /**
     * Метод позволяет сохранить новость
     * @param noticeForSaveDto - новость для сохранения
     * @throws EntityNotFoundException - если формат картинки не поддерживается приложением
     * @return - сохраненная новость
     */
    NoticeDto saveNotice(NoticeForSaveDto noticeForSaveDto) throws EntityNotFoundException;

    /**
     * Метод позволят редактировать новость
     * @param noticeForUpdateDto - новость для редактирования
     * @throws EntityNotFoundException - в случае если не найдена новость для редактирования
     * @return - отредактированная новость
     */
    NoticeDto updateNotice(NoticeForUpdateDto noticeForUpdateDto) throws EntityNotFoundException;

    /**
     * Метод позволяет удалять новость
     * @param id - id новости
     * @return - удалена или нет
     */
    boolean deleteNoticeById(Integer id);

    /**
     * Метод позволяет изменять активность новости
     * @param id - id новости активность которой нужно изменить
     * @param isActive - флаг активности
     * @return - текущее состояние новости
     */
    boolean changeNoticeActivityById(Integer id, Boolean isActive);
}
